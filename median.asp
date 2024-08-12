# Finner medianen av tre tall
# (dvs det "midterste").

ant_kall = 0

def min (a, b):
   global ant_kall; ant_kall = ant_kall + 1
   if a <= b: return a
   else: return b

def max (a, b):
   return -min(-a,-b)

def median (a, b, c):
   return max(min(a,b), min(max(a,b),c))

def test (a, b, c):
   global ant_kall 
   ant_kall = 0
   print("median(", a, b, c, ") er", median(a,b,c), "["+str(ant_kall)+" kall]")

test(1, 2, 3)
test(1, 3, 2)
test(2, 1, 3)
test(2, 3, 1)
test(3, 1, 2)
test(3, 2, 1)
