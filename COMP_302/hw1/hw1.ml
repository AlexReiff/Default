(* HOMEWORK 1 : COMP 302 Fall 2013 

Alexander Reiff
260504962
  
   PLEASE NOTE:  

   * All code files must be submitted electronically
     BEFORE class on 19 Sep, 2013

  *  The submitted file name must be hw1.ml 

  *  Your program must type-check and run using 
     OCaml of at least OCaml 3.11.2

  * Remove all "raise NotImplemented" with your solutions
*)

exception NotImplemented

(* ------------------------------------------------------------*)
(* QUESTION 2 : WARM-UP                                        *)
(* ------------------------------------------------------------*)
(* Q2.1: Average                                               *)
(* ------------------------------------------------------------*)
(* average: float list -> float

 The function takes a list of integers and returns
 their average as a floating point number
*)

let rec average l = 
    let rec avgHelper l sum count = match l with
        |[] -> (sum /. count)
        |(x::t) -> avgHelper t (sum +. x) (count +. 1.0)
    in avgHelper l 0.0 0.0



(* -------------------------------------------------------------*)
(* Q2.2:  Standard Deviation                                    *) 
(* -------------------------------------------------------------*)

(* stDev: int list -> real 

 The function takes a list of integers and returns their
 the standard deviation as a real number
*)

let square x = x *. x 

let rec stDev l =
    let rec stDevHelper avg l1 l2 = match l1 with
        |[] -> l2
        |(x::t) -> stDevHelper avg t (square(x-.avg)::l2)
    in sqrt(average( stDevHelper (average l) l []))


(* ------------------------------------------------------------*)
(* QUESTION 3                                                  *)
(* ------------------------------------------------------------*)

(* Partial sums :

   Given a list of integers, compute the partial sums 
   i.e. the sums of all the prefixes

   psum: int list -> int list

*)

let rec psum l =
    let rec pSumHelper l1 l2 sum = match l1 with
        |[] -> l2
        |(x::t) -> let y = (sum+x)::l2 in pSumHelper t y (sum+x)
    in List.rev(pSumHelper l [] 0)


(* Some test cases 
# psum [1;1;1;1;1];;
- : int list = [1; 2; 3; 4; 5]

# psum [1;2;3;4];;
- : int list = [1; 3; 6; 10]

# psum [];;
- : int list = []

# psum [9];
- : int list = [9]

*)


(* ------------------------------------------------------------*)
(* QUESTION 4 : Mobile                                         *) 
(* ------------------------------------------------------------*)

type mobile = Object of int | Wire of mobile * mobile

(* A object is represented by its weight (= an integer) and a wire
 is represented by the two mobiles attached to its ends 

*)

(* ------------------------------------------------------------*)
(* Q4.1: Weight                                                *)
(* ------------------------------------------------------------*)
(* val weight: mobile -> int
       weight(m) = total weight of the mobile m *)

let rec weight m = match m with
    |Object(n) -> n
    |Wire(x,y) -> weight(x) + weight(y)

(* ------------------------------------------------------------*)
(* Q4.2: Balance                                               *)
(* ------------------------------------------------------------*)
(* val balanced : mobile -> bool 
   balanced (m) ==> true, if weight of m's left end = weight of m's
                          right end; and each of the sub-parts are also 
			  balanced.
                   false otherwise

  Note: it is not simply enough to check that two
  children have the same weight == it still could mean
  that a sub-tree is unbalanced.

*)
let rec balanced m = match m with
    |Object(x) -> true
    |Wire(x,y) -> (weight x = weight y) && balanced x && balanced y


(* ------------------------------------------------------------*)
(* Q4.3: Reflection                                            *)
(* ------------------------------------------------------------*)
(* We can reflect a mobile about its verical axis: for an
 object, the reflection is just itself; for a wire, we swap the
 positions of the two mobiles hanging off its end. Reflection
 is applied recursively on the subparts. *)

(*   val reflect: mobile -> mobile 
   reflect(m) => a mobile that is the complete reflection of m
*)

let rec reflect m = match m with
    |Object(x) -> Object(x) 
    |Wire(x, y) -> Wire(reflect y, reflect x)

(* ------------------------------------------------------------*)
(* Q4.4 Weight of the Mobile                                   *)
(* ------------------------------------------------------------*)
(* We modify the representation of the mobile slightly and keep the
 weight information at the wire. The weight at the wire is the sum of
 the weight of each mobiles attached to it. 
*)
 
type rmobile = RObj of int | RWire of rmobile * int * rmobile

(* val rweight: rmobile -> int 
  constant time function which computes the total weight of an rmobile
  *)
let rec rweight m = match m with
    |RObj(x) -> x
    |RWire(_,x,_) -> x


(* ------------------------------------------------------------*)
(* QUESTION 5                                                  *)
(* ------------------------------------------------------------*)

(* Binary numbers *)
type bnum = E | Zero of bnum | One of bnum

(* Binary numbers are represented in REVERSE ORDER

   for example : 

    110 =  zero (one (one e)) =  6                 (no leading zero)
   0110 =  zero (one (one (zero e))) = 6           (one leading zero)
  00110 =  zero (zero (one (one (zero e)))) = 6    (two leading zero)
   0101 =  one (zero (one (zero e))) = 5           (no leading zero)

*)

(* ------------------------------------------------------------*)
(* QUESTION 5.1                                                *)
(* ------------------------------------------------------------*)

(* Write a function which converts an integer n (n >= 0)
   into a binary number with no leading zeros.
   
   intToBin : int -> bnum *)

let rec intToBin b = match b with
    |0 -> Zero(E)
    |_ -> if b mod 2 = 1 then One(intToBin(b/2)) else Zero(intToBin(b/2))

(* Some test cases

   - intToBin(5);
   val it = one (zero (one e)) : bnum

   - intToBin(6);
   val it = zero (one (one e)) : bnum

   - intToBin(8);
   val it = zero (zero (zero (one e))) : bnum

   - intToBin(7);
   val it = one (one (one e)) : bnum
  
*)

(* ------------------------------------------------------------*)
(* QUESTION 5.2                                                *)
(* ------------------------------------------------------------*)

(* Write a function which converts a binary number possibly with
   leading zeros into an integer n where n >= 0.

   binToInt : bnum -> int *)

let rec binToInt (b) = 
    let rec binHelper b p s = match b with
        |E -> s
        |Zero(x) -> binHelper x (p+1) s
        |One(x) -> binHelper x (p+1) (s + int_of_float(2.0**float_of_int(p)))
    in binHelper b 0 0

(* Some test cases :


# binToInt (One (Zero (One (Zero E))));;
- : int = 5
# binToInt (One (Zero (One (Zero (Zero E)))));;
- : int = 5
# binToInt (Zero (One (One E)));;
- : int = 6
# binToInt (Zero (One (One (Zero E))));;
- : int = 6
#  
*)


