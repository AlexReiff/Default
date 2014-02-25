(* Using modules for working with different metrics *)

module type METRIC = 
sig 
  type t 
  val unit : t 
  val plus : t -> t -> t 
  val prod : t -> t -> t 
  val toString : t -> string
  val toFloat  : t -> float
  val fromFloat : float -> t
end;;

(* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *)
(* Question 1.1 *)
(* Define a module Float which provides an implementation of 
   the signature METRIC; 

   We then want use the module Float to create different representations
   for Meter, KM, Feet, Miles, Celsius, and Fahrenheit, Hour 
*)
(* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *)

module Float : METRIC = 
struct
    type t = float 
    let unit = 1.0 
    let plus = ( +. ) 
    let prod = ( *. ) 
    let toString x = string_of_float x
    let toFloat x = x
    let fromFloat x = x
end;;

module Meter = (Float : METRIC);;
module KM = (Float : METRIC);;
module Feet = (Float : METRIC);;
module Miles = (Float : METRIC);;
module Celsius = (Float : METRIC);;
module Fahrenheit = (Float : METRIC);;
module Hour = (Float : METRIC);; 

(* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *)
(* Question 1.2 *)
(* Define a functor Speed which implements the module type SPEED. We 
   want to be able to compute the speed km per hour as well as 
   miles per hour. 

   The functor Speed must therefore be parameterized.
*)
(* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *)

module type SPEED = 
sig
  type s
  type distance 
  val speed :  distance -> Hour.t -> s 
  val average : s list -> s 
end;;

module Speed (M : METRIC) : (SPEED with type s = M.t with type distance = M.t)=
struct
    type s = M.t
    type distance = M.t
    let speed d t = M.fromFloat((M.toFloat d) /. (Hour.toFloat t))
    let rec avg l acc = match l with
        |[] -> acc
        |h::t -> avg t (acc +. M.toFloat h)
    let average l = let y = (avg l 0. /. ( (float) (List.length l)))
        in M.fromFloat y 
end;;

module MilesPerHour = Speed(Miles);;
module KMPerHour = Speed(KM);;

(* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *)
(* Question 1.3 *)
(* Show how to use the functor Speed to obtain an implementations
   for computing miles per hour in the module MilesPerHour and
   and implementation computing kilometers per hour in the module
   KMPerHour
*)
(* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *)

let s = KMPerHour.speed (KM.fromFloat (60.0)) Hour.unit
let valS = KM.toString s
 
let s = MilesPerHour.speed (Miles.fromFloat (60.0)) (Hour.fromFloat (2.0))
let valS = Miles.toString s

(* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *)
(* Question 1.4 *)
(* It is useful to convert between different metrics.

   Define a module type CONVERSION which specifies the following
   conversion functions:
   - feet2meter          meter = feet * 0.3048
   - fahrenheit2celsius  celsius = (fahrenheit - 32) / 1.8
   - miles2KM            km = miles * 1.60934
   - MilesPerHour2KMPerHour 

   Then implement a module which provides these conversion functions.
*)
(* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - *)

module type CONVERSION =
sig
    val feet2meter : Feet.t -> Meter.t
    val fahrenheit2celsius : Fahrenheit.t -> Celsius.t
    val miles2KM : Miles.t -> KM.t
    val milesPerHour2KMPerHour : MilesPerHour.s -> KMPerHour.s
end;;

module Conversion =
struct
    let feet2meter x = Meter.fromFloat ((Feet.toFloat x) *. 0.3048)
    let fahrenheit2celsius x = Celsius.fromFloat ((Fahrenheit.toFloat x -. 32.) /. 1.8)
    let miles2KM x = KM.fromFloat (Miles.toFloat x *. 1.60934)
    let milesPerHour2KMPerHour x = KMPerHour.speed (miles2KM (x)) Hour.unit
end;;
