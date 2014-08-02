

(* unify: typ * typ -> unit 

   unify(T1, T2) = () 
   
   succeeds if T1 and T2 are unifiable, fails otherwise.

   Side Effect: Type variables in T1 and T2 will have been
    updated, s.t. if T1 and T2 are unifiable AFTER unification
    they will denote the "same" type.
*)

open Type
module M = Minml

exception Error of string
exception Unimplemented

let freshVar () = TVar (ref None)

let rec occurs s t = match s, t with
  | _, Int -> false
  | _, Bool -> false
  | _, Arrow (t1, t2) ->
      (occurs s t1) || (occurs s t2)
  | _, Product ts ->
      List.exists (occurs s) ts
  | _, TVar r ->
   match !r with
    | None -> (s == r)
    | Some t' -> (s == r) || (occurs s t')

(* Question 4. *)
let rec unify s t = raise Unimplemented

(*match s,t with
  | TVar(a), TVar(b) -> match a,b with
    | Some(a), Some(b) -> if a = b then Error("No Error") else Error("type mismatch")
    | Some(a), None() -> t := Some(a)
    | None(), Some(b) -> s := Some(b)
    | None(), None() -> Error("No Error")
  | TVar(_), Arrow(t1,t2)   -> s := Some(Arrow(t1,t2))
  | Arrow(s1,s2), TVar(_)   -> t := Some(Arrow(s1,s2))
  | TVar(_), Product(tList) -> s := Some(Product(tList))
  | Product(sList), TVar(_) -> t := Some(Product(sList))
  | TVar(_), Int  -> s := Some(Int)
  | Int, TVar(_)  -> t := Some(Int)
  | TVar(_), Bool -> s := Some(Bool)
  | Bool, TVar(_) -> t := Some(Bool)
  | _,_ -> Error("No Error")*)

let unifiable (t, t') = 
  try
    let _ = unify t t' in true
  with Error s -> false
