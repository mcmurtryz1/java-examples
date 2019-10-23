//Zackary McMurtry
//CSC 364
//This class contains a method which heap sorts an array in-place

public class InPlaceIntHeapSort {

	public static void heapSort(int[] array) {
		int size = array.length;
		
		for (int x = 1; x < size; x++) {
			int pos = x;
			int parent = (pos - 1) / 2;
			
			//While our current position is "in bounds"
			//and greater than our parent, swap the two
			while (pos > 0 && array[parent] < array[pos]) {
				int temp = array[parent];
				array[parent] = array[pos];
				array[pos] = temp;
				pos = parent;
				parent = (pos - 1) / 2;
			}
		} 
		
		for (int y = size - 1; y > 0; y--) {
			//Swap index 0 and y
			int temp = array[0];
			array[0] = array[y];
			array[y] = temp;
			
			//Assign current pos & left and right child
			int pos = 0;
			int leftChild = (pos * 2) + 1;
            int rightChild = (pos * 2) + 2;
			
            //While one of our children are less than our "size"
			while (rightChild < y || leftChild < y) {
				//If the left child is greater than our size check the right child
                if (leftChild >= y) {
                    if (array[pos] < array[rightChild]) {
                    	temp = array[pos];
                    	array[pos] = array[rightChild];
                    	array[rightChild] = temp;
                        pos = rightChild;
                    } else { 
                        break;
                    }
                //If the right child is greater than our size check the left child
                } else if (rightChild >= y) {
                    if (array[pos] < array[leftChild]) {
                    	temp = array[pos];
                    	array[pos] = array[leftChild];
                    	array[leftChild] = temp;
                        pos = leftChild;
                    } else {
                        break;
                    }
                //If both children are inbounds, compare the smaller of the two
                } else {
                	int maxChild;
                    if (array[leftChild] > array[rightChild]) {
                    	maxChild = leftChild;
                    } else {
                    	maxChild = rightChild;
                    }

                    if (array[pos] < array[maxChild]) {
                    	temp = array[pos];
                    	array[pos] = array[maxChild];
                    	array[maxChild] = temp;
                        pos = maxChild;
                    } else {
                        break;
                    }
                }
                
                //Reassign new child values
                leftChild = (pos * 2) + 1;
                rightChild = (pos * 2) + 2;
            }
		}
	}
}