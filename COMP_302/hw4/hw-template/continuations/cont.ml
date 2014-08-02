module TailRec  = 
struct
  exception TODO

  let rec aux_filter f l cont = match l with
    |[]     -> cont []
    |(h::t) -> if f h then aux_filter f t (fun r -> cont (h::r))
               else  aux_filter f t cont
    
  let rec filter f l = aux_filter f l (fun r -> r)

(*
foldr : ('a -> 'b -> 'b) -> 'a list -> 'b -> 'b
foldr f [a1; ...; an] b is f a1 (f a2 (... (f an b) ...)). 
*)

  let rec aux_foldr f l b cont = match l with
    |[]     -> cont b
    |(h::t) -> aux_foldr f t b (fun r -> cont (f h r))

  let foldr f l b = aux_foldr f l b (fun r -> r)

end;;
