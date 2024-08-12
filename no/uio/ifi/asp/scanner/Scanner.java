// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo
package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;


    public Scanner(String fileName) {
	curFileName = fileName;
	indents.push(0);

	try {
	    sourceFile = new LineNumberReader(
			    new InputStreamReader(
				new FileInputStream(fileName),
				"UTF-8"));
	} catch (IOException e) {
	    scannerError("Cannot read " + fileName + "!");
	}
    }


    private void scannerError(String message) {
	String m = "Asp scanner error";
	if (curLineNum() > 0)
	    m += " on line " + curLineNum();
	m += ": " + message;

	Main.error(m);
    }


    public Token curToken() {
	while (curLineTokens.isEmpty()) {
	    readNextLine();
	}
	return curLineTokens.get(0);
    }


    public void readNextToken() {
	if (! curLineTokens.isEmpty())
	    curLineTokens.remove(0);
    }

    /*
    The following method read the next line of an asp program file
    */
    private void readNextLine() {
	curLineTokens.clear();

  /**
  * the following block of code Read the next line:
  * returning string of  a line  from an object of  LineNumberReader
  * i.e from sourceFile using readLine() methods, readLine method return string of LineNumberReader
  * if string of line obtained from sourceFile.readLine() is empty then
  * close the sourceFile and handle error Exception as scannerError
  *else noteSourceLine Make a note in the log file that a source line has been read.
  */
	String line = null;
	try {
	    line = sourceFile.readLine();
	    if (line == null) {
		sourceFile.close();
		sourceFile = null;
	    } else {
		Main.log.noteSourceLine(curLineNum(), line);
	    }
	} catch (IOException e) {
	    sourceFile = null;
	    scannerError("Unspecified I/O error!");
	}


  //-- Must be changed in part 1:

  /**  the following block of code perform:
 * if it is the empty line and on the last line
 * remove indents from Stack
 * add dedentToken to 	curLineTokens ArrayList
 * add eofToken  to 	curLineTokens ArrayList
 * write Log Line to showInfo on currentline
*/
	if (line == null) {
		// For all values of indents that are > 0
		while (indents.peek() > 0) {
			indents.pop();
			curLineTokens.add(new Token(dedentToken, curLineNum()));
		}

		curLineTokens.add(new Token(eofToken, curLineNum()));

		for (Token t: curLineTokens){
			Main.log.noteToken(t);
		}
		return;
	}

  /**     the following block of code perform:
  *  converts initial TAB characters to the correct number of blanke
     by expanding leading tabs
  * trim unneccessery blanck
  * find number of indents in the line
  * check for emptyline & commented line to ignore
  * push indents to stack and add token to arraylist
  * scann indention error to logfile
  */
	String newLine = expandLeadingTabs(line);

	// Trim end the line
	newLine.trim();

	// Compute the number of initial blanks at the beginning of the line
	int nbBlanks = findIndent(newLine);

	// When the line is empty or the line is a comment, just ignore it
	if (newLine.isEmpty() || newLine.length() <= nbBlanks || newLine.charAt(nbBlanks) == '#'){
		return;
	}

	// If the number of indents is greater than the peek of indents
	if (nbBlanks > indents.peek()){
		indents.push(nbBlanks);
		curLineTokens.add(new Token(indentToken, curLineNum()));
	}
	// Otherwise, as long as the number of indents is less than the peek of indents
	else if (nbBlanks < indents.peek()){
		while (nbBlanks < indents.peek()){
			indents.pop();
			curLineTokens.add(new Token(dedentToken, curLineNum()));
		}
	}

	if (nbBlanks != indents.peek()){
		scannerError("indentation error");
	}

  /** The following block of code loop through evry line to perform:
  * go through all line in the program and check the types of characters
  * determine tokenKind from the character either it is name, string, integer, float
    or operator inorder to  build token object
  * scann for this tokenkind error
  */

	int pos = 0;
	// Loop all characters of the line
	while (pos < line.length()) {
		char c = line.charAt(pos);
		if (pos < nbBlanks) {
			pos++;
			continue;
		}


// case 1 determin if char c is for nameToken tokenKind
		if (isLetterAZ(c)) {
			String termName = String.valueOf(line.charAt(pos));
			while (pos < line.length() - 1 && (isDigit(line.charAt(pos + 1)) || isLetterAZ(line.charAt(pos + 1)))) {
				termName += line.charAt(pos + 1);
				pos++;
			}
      // using static tokenKind property & values() method from java library
      // TokenKind.values() Returns an array containing the constants of this enum type, in the order they are declared
			TokenKind tokenKind = null;
			for (TokenKind tk: TokenKind.values()) {
				if (termName.equals(tk.toString())) {
					tokenKind = tk;
				}
			}

			if (tokenKind == null) {
				Token token = new Token(nameToken, curLineNum());
				token.name = termName;
				curLineTokens.add(token);
			} else {
				curLineTokens.add(new Token(tokenKind, curLineNum()));
			}
		}


  // case 2:  check for line character for condition of integer or floating number
    else if (isDigit(c) || c == '.') {
			boolean floatNumber = (c == '.');
			String numberStr = String.valueOf(line.charAt(pos));

			while (pos < line.length() - 1 && (isDigit(line.charAt(pos + 1)) || line.charAt(pos + 1) == '.')) {
				numberStr += line.charAt(pos + 1);
				if (line.charAt(pos + 1) == '.') {
					floatNumber = true;
				}
				pos++;
			}

			if (floatNumber) {
				if (numberStr.length() >= 3) {
					Token token = new Token(floatToken, curLineNum());
					Double floatN = Double.parseDouble(numberStr);
					token.floatLit = floatN;
					curLineTokens.add(token);
				} else{
					scannerError("Not valid float number: " + numberStr);
				}
			} else {
				Token token = new Token(integerToken, curLineNum());
				long longN = Long.parseLong(numberStr);
				token.integerLit = longN;
				curLineTokens.add(token);
			}
		}

// case 3 checking for char as stringtoken
     else if (c == '"' || c == '\'') {
			String str = "";
			boolean stringTermin = false;

			for (int nextPos = pos + 1; nextPos < line.length(); nextPos++) {
				char nextChar = line.charAt(nextPos);
				if ((c == '"' && nextChar == '"') || (c == '\'' && nextChar == '\'')) {
					stringTermin = true;
					break;
				} else {
					str += nextChar;
					pos = nextPos;
				}
			}

			if (!stringTermin) {
				scannerError("String is not terminated");
			}

			Token token = new Token(stringToken, curLineNum());
			token.stringLit = str;
			curLineTokens.add(token);
			pos++;
		}

// Case 4:  otherwise checking for rest tokenKind and or invalidity of token
    else if (!isLetterAZ(c) && c != ' ' && c != '#') {
			TokenKind tokenKind = null;
			for (TokenKind tk: TokenKind.values()) {
				if (String.valueOf(c).equals(tk.toString())) {
					tokenKind = tk;
				}
			}

			if (pos < line.length() - 1) {
				StringBuilder sb = new StringBuilder();
				sb.append(c);
				sb.append(line.charAt(pos + 1));
				TokenKind tokenKind2 = null;
				for (TokenKind tk: TokenKind.values()) {
					if (sb.toString().equals(tk.toString())) {
						tokenKind2 = tk;
					}
				}

				if (tokenKind != null && tokenKind2 == null) {
					curLineTokens.add(new Token(tokenKind, curLineNum()));
				} else if (tokenKind2 != null) {
					curLineTokens.add(new Token(tokenKind2, curLineNum()));
					pos++;
				} else {
					scannerError("This is not a valid token: <" + c + ">");
				}
			} else {
				curLineTokens.add(new Token(tokenKind, curLineNum()));
			}
		} else if (c == '#') {
			break;
		}
		pos++;
	}

	// Terminate line:  and write Log Line to show token Info on currentline
	curLineTokens.add(new Token(newLineToken,curLineNum()));

	for (Token t: curLineTokens) {
		if (t.kind != null) {
			Main.log.noteToken(t);
		}
	}
    }


    /////////////////////////////////////////////////////////////////////

    public int curLineNum() {
	return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
	int indent = 0;

	while (indent<s.length() && s.charAt(indent)==' ') indent++;
	return indent;
    }


    /**  The following method :
      * converts initial TAB characters to the correct number of blanke;
      *enable to determine the indentation of a line later in program
     @param s : string of line
     @return sb string
    */

    private String expandLeadingTabs(String s) {

      //-- Must be changed in part 1:

		int n = 0;
		int i = 0;
		for (i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

      // for each chararcter c in string line
       // case 1: If the character is blank, increase n by 1
			if (c == ' ') {
				n++;
			}
      // case 2: If the character is a TAB , replace it with 4 - ( n mod 4 ) blanks; &
      // increase n equivalent.
      else if (c == '\t') {
				n += (4 - (n % TABDIST));
			}

      // case 3: otherwise For all other characters, this loop ends.
      else {
				break;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < n; j++) {
			sb.append(' ');
		}
		sb.append(s.substring(i));

		return sb.toString();
    }


    private boolean isLetterAZ(char c) {
	return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
	return '0'<=c && c<='9';
    }

    public boolean isCompOpr() {
      TokenKind k = curToken().kind;
      //-- Must be changed in part 2:
      return (k == lessToken || k == greaterToken || k == doubleEqualToken ||
          k == greaterEqualToken || k == lessEqualToken || k == notEqualToken);
    }

    public boolean isFactorPrefix() {
      TokenKind k = curToken().kind;
      //-- Must be changed in part 2:
      return (k == plusToken || k == minusToken);
    }

    public boolean isFactorOpr() {
      TokenKind k = curToken().kind;
      //-- Must be changed in part 2:
      return (k == percentToken || k == slashToken || k == doubleSlashToken || k == astToken);
    }

    public boolean isTermOpr() {
      TokenKind k = curToken().kind;
      //-- Must be changed in part 2:
      return (k == plusToken || k == minusToken);
    }

    public boolean isArgument() {
      TokenKind k = curToken().kind;
      return (k == leftParToken);
    }

    public boolean isSubscription() {
      TokenKind k = curToken().kind;
      return (k == leftBracketToken);
    }

    public boolean anyEqualToken() {
      for (Token t: curLineTokens) {
        if (t.kind == equalToken) return true;
        if (t.kind == semicolonToken) return false;
      }
      return false;
    }
    }
