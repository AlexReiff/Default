(* HOMEWORK 2 : COMP 302 Fall 2013 
  
   NOTE:  

   All code files must be submitted electronically
   before class on Oct 3.

  The submitted file name must be hw2.ml 

  Your program must type-check using OCaml.

*)

exception NotImplemented

(* -------------------------------------------------------------*)
(* QUESTION 2 :  Warm-up [10 points]                            *) 
(* -------------------------------------------------------------*)
(* Given a function $f$ from reals to reals and a positive integer
 $n$ where $n > 0$), we want to define {\tt{repeated}} as a function
 that returns $f$  composed with itself $n$ times.  

 Stage your function properly such that it generates code which will
 apply the function $f$ to a given input $n$ times.  

*)

(* repeated: int -> ('a -> 'a) -> 'a -> 'a *)

let rec repeated n f =
	if n < 2 then
		fun x -> f(x)
	else
		let f' = repeated (n-1) f
		in fun x -> f(f'(x))

(* -----------------------------------------------------------------------------*)
(* QUESTION 3: Maximum Likelihood                                               *)
(* -----------------------------------------------------------------------------*)

let  fact n = 
  let rec factorial n = if n = 0 then 1 
    else  n * factorial (n-1)
  in
  if n <= 0 then 1 else factorial n

let binom (n, k) = 
  if n < k then 0.0
  else float (fact n) /. (float (fact k) *. float (fact (n - k)))

let simple_dist n = (n *. (n -. 1.0) *. (10.0 -. n)) /. 240.0

(* X = BlackMarbelsDrawn *)
let dist_black n x (marbelsTotal, marbelsDrawn) = 
  (binom (n, x) *. binom (marbelsTotal - n, marbelsDrawn - x)) 
  /. (binom (marbelsTotal, marbelsDrawn))

(* -----------------------------------------------------------------------------*)
(* Q3.1 : Compute the distribution table.                                       *)
(* -----------------------------------------------------------------------------*)
let rec tabulate f n = 
  let rec tab n acc = 
    if n = 0 then (f 0)::acc
    else tab (n-1) ((f n)::acc)
  in
  tab n []


let dist_table (marbelsTotal, marbelsDrawn) x =
    let dist n = dist_black n x (marbelsTotal, marbelsDrawn) in
    List.map  dist (tabulate (fun x -> x) marbelsTotal)

(* -----------------------------------------------------------------------------*)
(* Compute the maximum of the dist_table. The maximum corresponds to the number *)
(* of black marbels which is most likely to be in an urn *)

let max_in_list l = 
  let rec max_in_list' pos l = match l with 
    | [h]  -> (pos, h)
    | h::t -> 
      let (q, mx) = max_in_list' (pos+1) t in 
	if h < mx then (q,mx)
	else (pos, h) 
  in 
  let (pos, _) = max_in_list' 0 l in 
  pos

(* -----------------------------------------------------------------------------*)
(* Q 3.2: Test whether the matrix is empty                                      *)
(* -----------------------------------------------------------------------------*)

(*
let isEmpty
let getRows = List.map Matrix.get_row matrix (tabulate (fun x -> x) Matrix.nbrows matrix)
let boolList = List.map isEmpty getRows
*)
let emptyMatrix matrix = 
    let isEmpty x = if x = [] then true else false
    in let boolList = List.map isEmpty matrix
    in let truth x y = if x && y then true else false
    in List.fold_right truth boolList true
    
(* -----------------------------------------------------------------------------*)
(* Q 3.3: Compute the distribution matrix                                       *)
(* -----------------------------------------------------------------------------*)

let dist_matrix (total, drawn) resultList =
    let table n = dist_table (total,drawn) n
    in List.map table resultList


(* -----------------------------------------------------------------------------*)
(* Q 3.4: Compute the combined distribution table                               *)
(* -----------------------------------------------------------------------------*)

let rec helper l1 l2 result = match l1,l2 with
    |[],_ -> result
    |(h::t),[] -> helper t [] result@[h]
    |(h::t),(h2::t2) -> helper t t2 result@[h *. h2]

let rec combined_dist_table matrix = 
    let simpleHelper l1 l2 = helper l1 l2 []
    in List.fold_right simpleHelper matrix []

(* -----------------------------------------------------------------------------*)
(* Maximum Likelihood                                                           *)
let max_likelihood (total, drawn)  resultList = 
  max_in_list 
   (combined_dist_table  (dist_matrix (total, drawn) resultList))


(*

Example: 

Combined distribution table for Total = 10, Drawn = 3, 
and ResultList = [2,0]

[0. ; 0. ; 0.0311111111111111102 ; 
 0.0510416666666666657; 0.0499999999999999958;
 0.0347222222222222238; 0.0166666666666666664; 0.004375; 
 0.; 0.; 0.]

The maximum in this list is at position 3 (if the first element of the list is at position 0). Hence, it is most likely that there are 3 black marbels in the urn. 

*)

(* -------------------------------------------------------------*)
(* QUESTION 4 :  Tries                                          *) 
(* -------------------------------------------------------------*)

(* Dictonary *)
(* Implement a trie to look up strings in  a dictionary *)

(* A trie is an n-ary tree *)

type 'a trie = Node of 'a * ('a trie) list | Empty

(* -------------------------------------------------------------*)
(* QUESTION 4.1 : string manipulation  [10 points]              *) 
(* -------------------------------------------------------------*)

(* string_explode : string -> char list *)
let string_explode s =
	let rec explodeHelper i charList =
		if i < 0 then
			charList
		else
			explodeHelper (i - 1) (String.get s i :: charList)
	in explodeHelper (String.length s - 1) []

(* string_implode : char list -> string *)
let string_implode l =
	let stringList = List.map (Char.escaped) l
	in String.concat "" stringList

(* -------------------------------------------------------------*)
(* QUESTION 4.2 : Insert a string into a trie  [15 points]      *) 
(* -------------------------------------------------------------*)

(* Insert a word in a trie *)
(* ins: char list * (char trie) list -> char trie *)

(* Duplicate inserts are allowed *)
let rec ins l t = match l,t with
    | [], _ -> Empty::t
    | (h::t1), [] -> [Node(h, (ins t1 []))]
    | (h::t1), Empty::t2 -> Empty::(ins l t2)
    | (h::t1), (Node(a,aList) as l2)::t2 ->
        if a = h then
            (Node(h,(ins t1 aList)))::t2
        else
            l2::(ins l t2)

(* insert : string -> (char trie) list -> (char trie) list *)
let  insert s t = 
  let l = string_explode s in  (* turns a string into a char list *)    
  ins l t


(* -------------------------------------------------------------*)
(* QUESTION 4.3 : Lookup a string in a trie   [15 points]       *) 
(* -------------------------------------------------------------*)

let rec containsEmpty l = match l with
  | Empty::l -> true
  | _    ::l -> containsEmpty l
  | []       -> false

(* lkp : char list * (char trie) list -> bool *)
let rec lkp char_list trie_list = match trie_list with
    | [] -> false
    | Empty::t2 ->
        if char_list = [] then
            true
        else
            lkp char_list t2
    | Node(a,aList)::t2 -> match char_list with
        | [] -> lkp char_list t2
        | (h::t1) -> if a = h then
                         lkp t1 aList
                     else
                         lkp char_list t2

let rec lookup s t = 
  let l = string_explode s in (* l = char list *)    
    lkp l t

(* -------------------------------------------------------------*)
(* QUESTION 4.4 : Find all string in a trie   [15 points]       *) 
(* -------------------------------------------------------------*)
(* Find all strings which share the prefix p *)
  
exception Error 

(*findHelper: char list -> char list -> trie list -> string list*)
(*
let rec findAll' char_list trie_list = match trie_list with
    | [] -> match char_list with
            | [] -> []                     (*end of the word*)
            | _  -> raise Error            (*word is not in the trie*)
    | (h2::t2) -> match h2 with
        | Empty         -> findAll' char_list t2
        | Node(a,aList) -> match char_list with
            | [] -> let findWords = (List.map (fun s -> a::s) (findAll' [] aList))
                                    @(findAll' [] t2) in
                if (containsEmpty aList) then
                    [a]::findWords            (*the prefix is a full word*)
                else
                    findWords
            | (h::t) -> if h = a then
                            findAll' t aList
                        else
                            findAll' char_list t2
*)

let rec findAll' char_list trie_list = match char_list, trie_list with
    | [],[] -> []
    | _,[] -> raise Error
    | _,Empty::t2 -> findAll' char_list t2
    | [], Node(a,aList)::t2 ->
        let findWords = (List.map (fun s -> a::s) (findAll' [] aList))
                                    @(findAll' [] t2) in
                if (containsEmpty aList) then
                    [a]::findWords            (*the prefix is a full word*)
                else
                    findWords
    | (h::t1), Node(a,aList)::t2 ->
        if h = a then
            findAll' t1 aList
        else
            findAll' char_list t2

let findAll prefix trie_list = 
  begin try
    let char_list     = string_explode prefix  in 
    let postfix_list  = findAll' char_list trie_list in 
    let postfix_words = List.map (fun p -> string_implode p) postfix_list in
      List.map (fun w -> prefix^w) postfix_words
  with
     Error -> print_string "No word with this prefix found\n" ; []
  end


(* -------------------------------------------------------------*)
(* TEST CASE                                                    *) 
(* -------------------------------------------------------------*)

let t = 
 [Node
     ('b',
      [Node ('r' , [Node ('e' , [Node ('e' , [Empty])])]) ;
       Node ('e' , [Node ('d' , [Empty]) ;
		    Node ('e' , [Empty ; Node ('f', [Empty; Node ('y', [Empty])]) ;
				 Node ('r',[Empty])]) ;
		    Node ('a' , [Node ('r', [Empty; Node ('d' , [Empty])])])])])]
















