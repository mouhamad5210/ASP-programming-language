
# Finn alle primtall opp til n
# ved hjelp av teknikken kalt «Eratosthenes' sil».

n = 1000
primes = [True] * (n+1)

def find_primes ():
   for i1 in range(2,n+1):
      i2 = i1 * i1
      while i2 <= n:
         primes[i2] = False
         i2 = i2 + i1

def format (n, w):
   res = str(n)
   while len(res) < w: res = ' '+res
   return res

def list_primes():
    n_printed = 0
    line_buf = ''
    for i in range(2,n+1):
       if primes[i]:
          if n_printed > 0 and n_printed%10 == 0:
             print(line_buf)
             line_buf = ''
          line_buf = line_buf + format(i,4)
          n_printed = n_printed + 1
    print(line_buf)

find_primes()
list_primes()
