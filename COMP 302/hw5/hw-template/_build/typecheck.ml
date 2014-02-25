open Type
module M = Minml
open Unify
  
type context = Ctx of (M.name * tp) list

let empty = Ctx []

exception Unimplemented

exception NotFound
let rec assoc x y = match y with
  | [] -> raise NotFound
  | (y, r)::rest -> if x = y then r else assoc x rest

let rec lookup ctx x = let Ctx list = ctx in assoc x list
let extend ctx (x, v) = let Ctx list = ctx in Ctx ((x,v)::list)

let rec extend_list ctx l = match l with
  | [] -> ctx
  | (x,y) :: pairs -> extend_list (extend ctx (x, y)) pairs

exception TypeError of string

let fail message = raise (TypeError message)

let primopType p = match p with
  | M.Equals   -> Arrow(Product[Int; Int], Bool)
  | M.LessThan -> Arrow(Product[Int; Int], Bool)
  | M.Plus     -> Arrow(Product[Int; Int], Int)
  | M.Minus    -> Arrow(Product[Int; Int], Int)
  | M.Times    -> Arrow(Product[Int; Int], Int)
  | M.Negate   -> Arrow(Int, Int)

(* Question 3. *)

(* infer : context -> M.exp -> tp  *)
let rec infer ctx exp = raise Unimplemented

(*match exp with
  | Int (_) -> (Int)
  | Bool (_) -> (Bool)
  | If (e,e1,e2) -> if infer ctx e = Bool then let t = infer ctx e1 
                    in if t = infer ctx e2 then t else fail "type mismatch"
                    else fail "expression of type Bool required"
  | Primop (op, eList) -> match op,eList with 
    | Equals,   [Int i; Int i'] -> primopType op
    | LessThan, [Int i; Int i'] -> primopType op
    | Plus,     [Int i; Int i'] -> primopType op
    | Minus,    [Int i; Int i'] -> primopType op
    | Times,    [Int i; Int i'] -> primopType op
    | Negate,   [Int i]         -> primopType op
    | _                         -> fail "type mismatch"
  | Tuple (eList) -> let helper = match eList with
    | [] -> []
    | (h::t) -> (infer ctx h)::(infer ctx Tuple(t))
    in Product(helper)
  | Fn (name, t, e) -> raise Unimplemented
  | Rec (name, t, e) -> raise Unimplemented
  | Let (decList, e) -> raise Unimplemented
  | Apply (e1,e2) -> match (infer ctx e1), (infer ctx e2) with
    | Arrow(t1,t2), t -> if t1 = t then t2 else fail "incorrect input"
    | _ -> fail "type mismatch"
  | Var (name) -> raise NotFound
  | Anno (e,t) -> if infer ctx e = t then t else fail "type mismatch"
  | _ -> fail "IDK"
*)

