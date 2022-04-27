

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DecisionTree {
	//Root of the tree
		private Node Root;
		//number of nodes in decision tree
		private int DTsize;
		
		public int size() {
			return DTsize;		
		}
		
		public Node getRoot() {
			return Root;		
		}
		
		//create and return a new node r storing element e and
		//make r the root of the tree.
		public Node addRoot(String s) {
			Node r = new Node(s);
			Root = r;
			
			return Root;
		}
		
	    //create and return a new node w storing element el and 
	    //make w the left (yes) child of v. Make sure to increment the size.
	    //make sure to update both child's parent link and parent's child link.
	    //throws a RuntimeException if the node has already a left child.
		public Node insertYes(Node v, String el) throws RuntimeException{
			Node w = new Node(el);
			if(v.hasLeft() == true)	throw new RuntimeException();
			else{
				
			v.setLeft(w);
			w.setParent(v);
			DTsize++;
			
			return w;
			}
		}

	    //create and return a new node w storing element el and 
	    //make w the right (no) child of v. Make sure to increment the size.
	    //make sure to update both child's parent link and parent's child link.
	    //throws a RuntimeException if the node has already a right child.
		public Node insertNo(Node v, String el) throws RuntimeException{
			Node w = new Node(el);
			if(v.hasRight() == true)	throw new RuntimeException();
			else{
				
			v.setRight(w);
			w.setParent(v);
			DTsize++;
			
			return w;
			}	
		}
		
	    //remove node v, replace it with its child, if any, and return 
	    //the element stored at v. make sure to decrement the size.
	    //make sure to update both child's parent link and parent's child link.
	    //throws a RuntimeException if v has two children
		public String remove(Node v) throws RuntimeException{
			String s = v.getElement();
			if(v.hasRight() && v.hasLeft())	throw new RuntimeException();
			else if(v.hasRight() || v.hasLeft()) {
				Node n;
				if(v.hasRight())	n = v.getRight();
				else if(v.hasLeft()) n = v.getLeft();
				else
					return "-1";
				
				if(v.getParent().getLeft().equals(v))	v.getParent().setLeft(n);
				else if(v.getParent().getRight().equals(v)) v.getParent().setRight(n);
				else
					return "-1";
			}
			else {
				v = null;
			}
			DTsize--;
			return s;
		}

	    //write to file in PREORDER traversal order
	    //Handle file exceptions as follows: If an exception is thrown, return false.
	    //Otherwise, return true.	
		public boolean save(String filename) {

			File file = new File(filename);
			ArrayList<String> list1 = new ArrayList<String>();
			traverse(this.Root, list1);
			
			try {
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				
				for(String s : list1) {
					bw.write(s);
					bw.newLine();
				}
				
				bw.close();
				fw.close();
			} catch (FileNotFoundException e) {
				return false;
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		public void traverse(Node root, ArrayList<String> list1) {
			list1.add(root.getElement());
			
			if(root.hasLeft())
				traverse(root.getLeft(), list1);
			if(root.hasRight())
				traverse(root.getRight(), list1);
		}
		
	    //load the DT from file that contains the output of PREORDER traversal
	    //Handle file exceptions as follows: If an exception is thrown, return false.
	    //Otherwise, return true.
	    //You can distinguish a leaf node from an internal node as follows: internal nodes always end with
	    //a question mark.	
		public boolean load(String filename) {

			File file = new File(filename);
			ArrayList<String> list1 = new ArrayList<String>();
			
			try {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				
				String line = br.readLine();
				
					while(line != null) {
						list1.add(line);
						line = br.readLine();
					}
					
				br.close();
				fr.close();
			} catch (FileNotFoundException e) {
				
				return false;
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			Root = new Node();
			fillPre(this.Root, list1);
			return true;
		}
		public void fillPre(Node root, ArrayList<String> list1) {
			if(list1.isEmpty()) return;
			
			String s = list1.get(0);
			list1.remove(0);
			root.setElement(s);	
			if(s.indexOf('?') == -1) return;//leaf node kontrolu
			else 
			{
				Node tmp = new Node();
				tmp.setParent(root);
				root.setLeft(tmp);
				fillPre(tmp, list1);
				
				Node tmp2 = new Node();
				tmp2.setParent(root);
				root.setRight(tmp2);
				fillPre(root.getRight(), list1);
			}
		}
}
