/*

There are 4 steps , firsty you need to create a k-ary tree 

after that you need to create 3 functions 

lock , unlock , upgrade





lock --> if you are locking any node i.e if i am locking a node X with userid uid then i cannot lock any node which is descendent of node X and also i cannot lock any node  which is predecessor of node X

unlock --> it unlocks a node X only if it is locked and it is locked by the same user by which it is getting unlocked(uid must be same for both locking and unlocking)

upgrade --> upgrade will only work when atleast one of the descendent is locked and all the descendents which are locked should be locked by the same id as of current locking id.

NOTE :- while upgrading a node there should not be any ancestor which is locked if present then return false i.e you cannot perform upgrade on current node

*/



import java.util.*;

public class JuspayTreeOfSpace {
	private static class Node {
		boolean isLocked;
		int id;
		Node parent;
		int ancLockedCount , decsLockedCount;
		ArrayList<Node> childs; 
		Node() {
			this.isLocked = false;
			this.id = 0;
			this.parent = null;
			this.ancLockedCount = this.decsLockedCount = 0;
			this.childs = new ArrayList<Node>();
		}

		Node(Node parent) {
			this.isLocked = false;
			this.id = 0;
			this.parent = parent;
			this.ancLockedCount = this.decsLockedCount = 0;
			this.childs = new ArrayList<Node>();	
		}
	} 

	private class Tree {
		private static void informDesc(Node node , int value) {
			if(node == null) return;
			node.ancLockedCount += value;	
            for(var child : node.childs) informDesc(child , value);
		}

		// LOCKING METHOD

		// lock the node with the id as : 'id'

		// if the node is already locked then return false;

		// if any of the ancestors or descendants are locked then also return false 

		// else lock the 'node' and inform all the ancestors and descendants that the 'node' is locked



		static boolean lockNode(Node node , int id) {
			if(node.isLocked == true) return false;
			if(node.ancLockedCount > 0 || node.decsLockedCount > 0) return false;

			var parent = node.parent;
      
			// informing the ancs that the Node 'node' is locked
			while(parent != null) {
				parent.decsLockedCount++;
				parent = parent.parent;
			} 

			// informing the desc.. that the Node 'node' is locked
			informDesc(node , 1);

			node.isLocked = true;
			node.id = id;
			return true;
		}

		// UNLOCKING METHOD

		// Unlock the node if and only if it is locked

		// The node must be unlocked by the same id from which it was locked		

		static boolean unlockNode(Node node , int id) {
			if(node.isLocked == false) return false;
			if(node.id != id) return false;

			var parent = node.parent;

			// informing the ancestors of the 'node' that we are unlocking the 'node'
			while(parent != null) {
				parent.decsLockedCount--;
				parent = parent.parent;
			}

			// informing the desc.. that we are unlocking the 'node'
			informDesc(node , -1);
			node.isLocked = false;
			node.id = 0;
			return true;
		}


		private static boolean getChilds(Node node , ArrayList<Node> lockedChilds , int id) {
			if(node == null) return true; 
			if(node.isLocked) {
				if(node.id != id) return false;
				lockedChilds.add(node);
			}

			if(node.decsLockedCount == 0) return true;
			for(var child : node.childs) {
				var ok = getChilds(child , lockedChilds , id);
				if(!ok) return false;
			}
			return true;
		}

		// UPGRADE METHOD

		// Upgrade will only work if atleast one of the desc... are locked and if are locked then they must be locked with the same id

		static boolean upgradeNode(Node node , int id) {
			if(node.isLocked == true || node.decsLockedCount <= 0 || node.ancLockedCount > 0) return false; 
			
            var lockedChilds = new ArrayList<Node>();
			var ok = getChilds(node , lockedChilds , id);
			if(!ok) return false;

			informDesc(node , 1);
			for(var child : lockedChilds) unlockNode(child , id);
			lockNode(node , id);
			return true;
		}

		// build the Kry Tree
		static HashMap<String , Node> buildTree(String[] arr , int k) {
			var root = new Node();
			var stringToNode = new HashMap<String , Node>();
			stringToNode.put(arr[0] , root);

			var q = new ArrayDeque<Node>();
			q.offer(root);
			int idx = 1;
			int n = arr.length;

			while(!q.isEmpty() && idx < n) {
				int sz = q.size();
				while(sz-- > 0) {
					Node front = q.poll();
					for(int i = 1 ; i <= k && idx < n ; i++) {
						Node newNode = new Node(front);
						stringToNode.put(arr[idx++] , newNode);
						front.childs.add(newNode);
						q.offer(newNode);
					}
				}
			}

			return stringToNode;
		}
	}

	private static Scanner scn;
	public static void main(String[] args) { 
		System.out.println("Starting Code !!");
		scn = new Scanner(System.in);
		int n = scn.nextInt();
		int k = scn.nextInt();
		int q = scn.nextInt();

		var arr = new String[n];
		for(int i = 0 ; i < n ; i++) arr[i] = scn.next();
		var tree = Tree.buildTree(arr , k);

		for(int query = 0 ; query < q ; query++) {
			int option = scn.nextInt();
			String str = scn.next();
			int id = scn.nextInt();

			var node = tree.get(str);
			var ans = false;
			if(option == 1) ans = Tree.lockNode(node , id);
			else if(option == 2) ans = Tree.unlockNode(node , id);
			else if(option == 3) ans = Tree.upgradeNode(node , id);
			System.out.println(ans);
		}
    }
}
