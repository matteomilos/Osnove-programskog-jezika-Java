package hr.fer.zemris.java.custom.collections;

import java.util.LinkedList;

public class LinkedListIndexedCollection extends Collection {
	
	private static class ListNode{
		ListNode previous;
		ListNode next;
		Object valueStorage;
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	protected LinkedListIndexedCollection(){
		this.first=null;
		this.last=null;
	}
	
//	protected LinkedListIndexedCollection(Collection collection){
//		this.size=collection.size();
//		this.first=collect
//	}

}
