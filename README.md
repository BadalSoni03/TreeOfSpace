# TreeOfSpace

The code of round A of Hackathon of Juspay (without thread safe).

You are given an N-ry tree with N nodes and each node having atmost k childs.

You have to answer q query of the type {operation , node , id}.

You have to perform 4 operations which are:

1.) Build the Kry tree

2.) Lock the node with id

3.) Unlock the node with id

4.) Upgrade the node with id

The description on the operations are:

Lock operation :

    Lock operation cant be performed on the already locked node
    A node can only be locked if none of the ancestors and descendents are locked

Unlock operation  

    Unlock the node if and only if it is locked
    The node must be unlocked by the same id from which it was locked earlier

Upgrade operation

    Upgrade will only work if atleast one of the desc... are locked and if are locked then they must be locked with the same id


CONTRAINTS : 

    1 < N < 5 * 1e5 (Number of nodes)
   
    1 < M < 30 (Number of atmost childs each node can have)
  
    1 < Q < 5 * 1e5 (Number of queries)
 
    1 < Nodename < 20 (Length of the name of nodes) 

Note : Since the contraints are high and can lead to cubic time complexity while answering the queries, so a logarithmic solution is expected. And also the tree is balanced.

Refer https://github.com/BadalSoni03/JuspayTreeOfSpace-Part-B/tree/main for part B's correct solution. 
