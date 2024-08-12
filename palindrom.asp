
# A palindrome is a word that reads the same
# forwards and backwards, like OBO and RACECAR.

def is_a_palindrome (word):
   i1 = 0;  i2 = len(word)-1
   while i1 < i2:
      if word[i1] != word[i2]: return False
      i1 = i1 + 1;  i2 = i2 - 1
   return True

query = input("A word: ")
print('"'+query+'":', is_a_palindrome(query))
