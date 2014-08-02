package a1posted;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class TestIndexedHeap1 {
	private final String smallPNode = "testNodeSmallPriorityValue";
	private final String largePNode = "testNodeLargePriorityValue";
	private final String largestPNode = "testNodeLargestPriorityValue";
	
	IndexedHeap pq;
	
	@Before
	public void setup() {
		pq = new IndexedHeap();
	}
	
	@Test
	public void shouldAddSingleNode() {
		pq.add("testNode", 1.0);
		assertEquals(1, pq.sizePQ());
		assertTrue(pq.contains("testNode"));
		assertEquals(1.0, pq.getMinPriority(), 0.01);
	}
	
	@Test
	public void shouldAddMultipleNodesAndOrderThem() {
		pq.add(largePNode, 2.0);
		pq.add(smallPNode, 1.0);
		pq.add(largestPNode, 3.0);
		assertEquals(3, pq.sizePQ());
		assertEquals(1.0, pq.getMinPriority(), 0.01);
		assertTrue(pq.contains(largePNode));
		assertTrue(pq.contains(smallPNode));
		assertTrue(pq.contains(largestPNode));
		assertEquals(smallPNode, pq.nameOfMin());
	}
	
	@Test
	public void shouldRemoveMinProperly() {
		pq.add(largePNode, 2.0);
		pq.add(smallPNode, 1.0);
		pq.add(largestPNode, 3.0);
		assertEquals(3, pq.sizePQ());
		assertEquals(1.0, pq.getMinPriority(), 0.01);
		assertTrue(pq.contains(largePNode));
		assertTrue(pq.contains(smallPNode));
		assertTrue(pq.contains(largestPNode));
		assertEquals(smallPNode, pq.nameOfMin());
		
		String removedMin = pq.removeMin();
		assertEquals(smallPNode, removedMin);
		assertEquals(2, pq.sizePQ());
		assertEquals(2.0, pq.getMinPriority(), 0.01);
		assertTrue(pq.contains(largePNode));
		assertFalse(pq.contains(smallPNode));
		assertTrue(pq.contains(largestPNode));
		assertEquals(largePNode, pq.nameOfMin());
	}

	@Test
	public void shouldNotAddExistingNode() {
		pq.add("testNode", 1.0);
		try {
			pq.add("testNode", 2.0);
			fail("Failed to catch Exception when trying to add existing node");
		} catch (IllegalArgumentException e) {
		}
	}
	
//	@Test
//	public void shouldSwapElementsProperly() {
//		pq.add(largePNode, 2.0);
//		pq.add(smallPNode, 1.0);
//		assertEquals(1.0, pq.getMinPriority(), 0.01);
//		assertEquals(smallPNode, pq.nameOfMin());
//		pq.publicSwap(1, 2);
//		assertEquals(2.0, pq.getMinPriority(), 0.01);
//		assertEquals(largePNode, pq.nameOfMin());
//	}
	
	@Test
	public void shouldChangePriorityProperly() {
		pq.add(largePNode, 2.0);
		pq.add(smallPNode, 1.0);
		assertEquals(1.0, pq.getMinPriority(), 0.01);
		assertEquals(smallPNode, pq.nameOfMin());
		
		pq.changePriority(smallPNode, 3.0);
		pq.changePriority(smallPNode, 3.0); //calling changePriority with current value should not do anything
		assertEquals(2.0, pq.getMinPriority(), 0.01);
		assertEquals(largePNode, pq.nameOfMin());

		pq.changePriority(largePNode, 4.0);
		assertEquals(3.0, pq.getMinPriority(), 0.01);
		assertEquals(smallPNode, pq.nameOfMin());
		
		pq.changePriority(largePNode, 5.0);
		assertEquals(3.0, pq.getMinPriority(), 0.01);
		assertEquals(smallPNode, pq.nameOfMin());
		
	}
	
	/*   
	 *  Add a list of names (v_i) and associated random priorities into the heap.
	 *  Then change the priority of each v_i so that the ith random element has priority i.
	 *  Each such change could rearrange the elements to preserve the heap property.
	 *  Then remove the elements one by one from the heap.  They should be removed in sorted order,
	 *  and, because of how we changed the priorities, we should have (v_i, i) for the names and 
	 *  priorities. 
	 *  
	 */  

	public static void main(String[] args) {
		
		Random generator = new Random();
		double d;

		//   Here the names of the elements will be of type String. 
		//   I am naming the heap "pq" just as a reminder what the heap is for. 
		
		IndexedHeap  pq = new IndexedHeap();

		/*    Now add some elements into the heap.
		 *    The name of element i is v_i.  For example, these might be the names of
		 *    vertices in a graph.   The priority is a random number.   Adding a sequence
		 *    of them should produce a heap. 
		 */

		int numElements = 200;
		for (int i=1; i <= numElements; i++){
			
			//  give this element a random priority
			
			d = generator.nextDouble();
			pq.add( "v_" + new Integer(i).toString() , d);
		}

		/*
		 *  Change the priority so that the ith random element has priority i.
		 */
		
		for (int i=1; i <= numElements; i++)
			pq.changePriority("v_" + new Integer(i).toString(), i*1.0);

		System.out.println("\nRemoving all the elements (in order of priority) ");

		/*   Remove all elements from the priority queue.  v_i should have priority i.
		 * 	 They should print out in order of priority,  (v_1, 1.0),  (v_2, 2.0), etc.
		 * 	 Notice we can use an array now since we know the number of elements in the heap.
		 */

		String    names[]      = new String[numElements]; 
		double    priorities[] = new double[numElements]; 

		for (int i=0; i < numElements; i++){
			priorities[i] = pq.getMinPriority();			
			names[i] = pq.removeMin();
		}

		for (int i=0; i < numElements; i++){
			System.out.println( String.valueOf(names[i]) + " " + String.valueOf(priorities[i])    );
		}
	}

}
