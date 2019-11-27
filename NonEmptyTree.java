package tree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 *  
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	/* Provide whatever instance variables you need */
	protected K key;
	protected V value;
	public  Tree<K,V> left;
	public Tree<K,V> right;

	/**
	 * Only constructor we need.
	 * @param key
	 * @param value
	 * @param left
	 * @param right
	 */
	public NonEmptyTree(K key, V value, Tree<K,V> left, Tree<K,V> right) {
		//throw new UnsupportedOperationException("You must implement this method.");

		this.key = key;
		this.value = value;
		this.right = right;
		this.left = left;

	}

	/**
	 * Compares two objects of K type and return integer
	 * return less than 0 if first parameter is less than second
	 * return greater than 0 if first is greater than second else return 0
	 * @param key1
	 * @param key2
	 * @return int
	 */
	private int compare(K key1, K key2){
		
		return key1.compareTo(key2);
	}

	/**
	 * Find the value that this key is bound to in this tree.
	 * 
	 * @param key --
	 *            Key to search for
	 * @return -- value associated with the key by this Tree, or null if the key
	 *         does not have an association in this tree.
	 */
	public V search(K key) {

		if(compare(this.key,key) == 0){
			
			return this.value;
			
		}else if(compare(this.key,key) < 0){
			
			return this.right.search(key);
			
		}else{
			
			return this.left.search(key);
			
		}
	}

	/**
	 * Insert/update the Tree with a new key:value pair. If the key already
	 * exists in the tree, update the value associated with it. If the key
	 * doesn't exist, insert the new key value pair.
	 * 
	 * This method returns a reference to an Tree that represents the updated
	 * value. In many, but not all cases, the method may just return a
	 * reference to this. This method is annotated @CheckReturnValue because
	 * you have to pay attention to the return value; if you simply invoke insert on
	 * a Tree and ignore the return value, your code is almost certainly wrong. 
	 * 
	 * @param key --
	 *            Key
	 * @param value --
	 *            Value that the key maps to
	 * @return -- updated tree
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		
		if(compare(this.key,key) == 0){
			
			this.value = value;
		
		}else if(compare(this.key, key) > 0){
		
			this.left = this.left.insert(key, value);
		}else{
			
			this.right = this.right.insert(key, value);
		
		}
		
		return this;
	}

	/**
	 * Delete any binding the key has in this tree. If the key isn't bound, this
	 * is a no-op
	 * 
	 * This method returns a reference to an Tree that represents the updated
	 * value. In many, but not all cases, the method may just return a
	 * reference to this. This method is annotated @CheckReturnValue because
	 * you have to pay attention to the return value; if you simply invoke delete on
	 * a Tree and ignore the return value, your code is almost certainly wrong. 
	 * 
	 * @param key --
	 *            Key
	 * @return -- updated tree
	 */
	public Tree<K, V> delete(K key) {

		if(compare(this.key, key) == 0){
			
			try{
			
				this.value = search(this.left.max());
				this.key = this.left.max();
				this.left = this.left.delete(this.left.max());
			
			}catch (TreeIsEmptyException e){
				
				return this.right;
			}	

		}else if(compare(this.key, key) < 0){
			
			this.right = this.right.delete(key);
		}else{
			
			this.left = this.left.delete(key);
		}
		
		return this;
	}

	/**
	 * Return the maximum key in the subtree
	 * 
	 * @return maximum key
	 * @throws TreeIsEmptyException if the tree is empty
	 */
	public K max() {

		try{
			
			return this.right.max();
		}catch(TreeIsEmptyException e){
			
			return this.key;
		}

	}

	/**
	 * Return the minimum key in the subtree
	 * 
	 * @return minimum key
	 * @throws TreeIsEmptyException if the tree is empty
	 */
	public K min() {

		try{
		
			return this.left.min();
		}catch(TreeIsEmptyException E){
			
			return this.key;
		}
	}

	/**
	 * Return number of keys that are bound in this tree.
	 * 
	 * @return number of keys that are bound in this tree.
	 */
	public int size() {

		return 1+this.left.size()+this.right.size();

	}

	/**
	 * Add all keys bound in this tree to the collection c.
	 * The elements can be added in any order.
	 */
	public void addKeysToCollection(Collection<K> c) {

		c.add(this.key);
		this.right.addKeysToCollection(c);
		this.left.addKeysToCollection(c);
	}

	/**
	 * Returns a Tree containing all entries between fromKey and toKey, inclusive.
	 * It may not modify the original tree (a common mistake while implementing this method).
	 * 
	 * @param fromKey - Lower bound value for keys in subtree
	 * @param toKey - Upper bound value for keys in subtree
	 * @return Tree containing all entries between fromKey and toKey, inclusive
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {

		if(compare(this.key,toKey) > 0){

			return this.left.subTree(fromKey, toKey);
		}else if(compare(this.key,fromKey) < 0){
			
			return this.right.subTree(fromKey, toKey);
		}
		
		NonEmptyTree<K,V> result = 
				new NonEmptyTree<K,V>(this.key, this.value, this.left.subTree(fromKey, toKey), this.right.subTree(fromKey, toKey));
		
		return result;
	}

	/**
	 * Returns the height (maximum level) in the tree.  A tree with only one
	 * entry has a height of 1.
	 * @return height of the tree.
	 */
	public int height() {

		return 1+ Math.max(this.left.height(), this.right.height());
	}

	/**
	 * Performs the specified task on the tree using an inorder traversal.
	 * @param p object defining task
	 */
	public void inorderTraversal(TraversalTask<K,V> p) {
		
		this.left.inorderTraversal(p);
		p.performTask(this.key, this.value);
		this.right.inorderTraversal(p);
	}

	/**
	 * Performs the specified task on the tree using a right tree, root, left tree
	 * traversal.
	 * @param p object defining task
	 */
	public void rightRootLeftTraversal(TraversalTask<K,V> p) {
		
		this.right.rightRootLeftTraversal(p);
		p.performTask(this.key, this.value);
		this.left.rightRootLeftTraversal(p);
	}	
}