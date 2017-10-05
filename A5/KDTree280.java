/**
 * Kristine Trinh
 * nlt895
 * 11190412
 */
package lib280.tree;

import lib280.base.NDPoint280;
import lib280.exception.InvalidArgument280Exception;

import java.util.ArrayList;

public class KDTree280 {

    private int dim;
    private KDNode280 root;

    /**
     * @param pointArray set of points to build K-D-Tree
     * @param dim        Dimensionality of the point tree
     */
    public KDTree280(NDPoint280[] pointArray, int dim) {
        for (NDPoint280 ndPoint280 : pointArray) {
            if (ndPoint280.dim() != dim) throw new InvalidArgument280Exception("dimension of array must be " + dim);
        }
        this.dim = dim;
        root = kdTree(pointArray, 0, pointArray.length - 1, 0);
    }

    /**
     * Build K-D-Tree
     *
     * @param pointArray set of points to build K-D-Tree
     * @param left       offset of start of subarray from which to build a kd
     * @param right      offset of end of   subarray from which to build a kd - tree
     * @param depth      the current  depth in the partially built tree
     * @return root of tree
     */
    private KDNode280 kdTree(NDPoint280[] pointArray, int left, int right, int depth) {
        if (pointArray == null) return null;
        if (left > right) return null;
        int d = depth % dim;
        int medianOffset = (left + right) / 2;
        jSmallest(pointArray, left, right, d, medianOffset);
        KDNode280 node = new KDNode280(pointArray[medianOffset]);
        node.setLeft(kdTree(pointArray, left, medianOffset - 1, depth + 1));
        node.setRight(kdTree(pointArray, medianOffset + 1, right, depth + 1));
        return node;
    }

    /**
     * @param list  array of comparable elements
     * @param left  offset of start of subarray for which we want the median element
     * @param right offset of end of subarray for which we want the median element
     * @param d     dimension (coordinate) of the points is to be used to compare points
     * @param j     we want to find the element that belongs at array index j
     */
    private void jSmallest(NDPoint280[] list, int left, int right, int d, int j) {
        if (right > left) {
            int pivotIndex = partition(list, left, right, d);
            if (j < pivotIndex) jSmallest(list, left, pivotIndex - 1, d, j);
            else if (j > pivotIndex) {
                jSmallest(list, pivotIndex + 1, right, d, j);
            }
        }
    }

    /**
     * Partition a subarray using its last element as a pivot
     *
     * @param list  array of comparable elements
     * @param left  lower limit on subarray to be partitioned
     * @param right upper limit on subarray to be partitioned
     * @param d     dimension (coordinate) of the points is to be used to compare points
     * @return
     */
    private int partition(NDPoint280[] list, int left, int right, int d) {
        double pivot = list[right].idx(d);
        int swapOffset = left;
        for (int i = left; i < right; i++) {
            if (list[i].idx(d) <= pivot) {
                swap(list, i, swapOffset);
                swapOffset += 1;
            }
        }
        swap(list, right, swapOffset);
        return swapOffset;
    }

    /**
     * swap 2 elements in array
     *
     * @param list array with points
     * @param i1   index 1
     * @param i2   index 2
     */
    private void swap(NDPoint280[] list, int i1, int i2) {
        NDPoint280 t = list[i1];
        list[i1] = list[i2];
        list[i2] = t;
    }

    /**
     * find nodes in range between min and max points
     *
     * @param min lower border
     * @param max upper border
     * @return
     */
    public ArrayList<NDPoint280> rangeSearches(NDPoint280 min, NDPoint280 max) {
        ArrayList<NDPoint280> res = new ArrayList<>();
        rangeSearches(res, root, min, max);
        return res;
    }

    /**
     * find nodes in range between min and max points
     *
     * @param res  array which store result
     * @param node current node
     * @param min  lower border
     * @param max  upper border
     */
    private void rangeSearches(ArrayList<NDPoint280> res, KDNode280 node, NDPoint280 min, NDPoint280 max) {
        if (node == null) return;
        if (comparePoints(node.getKey(), min) && comparePoints(max, node.getKey())) {
            res.add(node.getKey());
        }
        rangeSearches(res, node.getLeft(), min, max);
        rangeSearches(res, node.getRight(), min, max);
    }


    /**
     * compare two ndPoints
     *
     * @param nd1 first node
     * @param nd2 second node
     * @return true if all coordinates if first node bigger or equal than coordinates of second node
     */
    public boolean comparePoints(NDPoint280 nd1, NDPoint280 nd2) {
        if (nd1.dim() != nd2.dim())
            throw new IllegalArgumentException("NDPoint: comparing two points of different dimension");


        for (int i = 0; i < this.dim; i++) {
            if (nd1.idx(i) < (nd2.idx(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * print KDTree
     */
    public void toStringByLevel() {
        toStringByLevel(root, 1);
    }

    /**
     * recursive method which print KDTree
     *
     * @param node  node wich will be printed
     * @param depth depth of node
     */
    private void toStringByLevel(KDNode280 node, int depth) {
        String tab = "";
        for (int i = 1; i < depth; i++) {
            tab += "\t";
        }
        if (node == null) {
            System.out.println(tab + depth + ": -");
            return;
        }
        toStringByLevel(node.getRight(), depth + 1);
        System.out.println(tab + depth + ": " + node.toString());
        toStringByLevel(node.getLeft(), depth + 1);
    }

    public static void main(String[] args) {
        System.out.println("Input 2 D points : (5.0  , 2.0)   (9.0 ,  10.0)   (11.0 ,  1.0) (4.0 ,  3.0) \n(2.0 ,  12.0) (3.0 ,  7.0)   (1.0 ,  5.0) " +
                "\nThe  2 D  tree  built  from  these  points  is :  ");
        NDPoint280[] points = new NDPoint280[]{new NDPoint280(new Double[]{5.0, 2.0})
                , new NDPoint280(new Double[]{9.0, 10.0}), new NDPoint280(new Double[]{11.0, 1.0})
                , new NDPoint280(new Double[]{4.0, 3.0}), new NDPoint280(new Double[]{2.0, 12.0})
                , new NDPoint280(new Double[]{3.0, 7.0}), new NDPoint280(new Double[]{1.0, 5.0})};
        KDTree280 d2Tree = new KDTree280(points, 2);
        d2Tree.toStringByLevel();
        System.out.println();
        System.out.println("Input 3 D points : (1.0 , 12.0 , 1.0) (18.0 , 1.0  , 2.0)  (2.0  ,  12.0 ,  16.0)  (7.0  ,  3.0 ,  3.0)  (3.0  ,  7.0 ,  5.0)  (16.0  ,  4.0 ,  4.0)  (4.0  ,  6.0 ,  1.0)  (5.0 ,  5.0 ,  17.0)" +
                "\nThe  3 D  tree  built  from  these  points  is :  ");
        points = new NDPoint280[]{
                new NDPoint280(new Double[]{1.0, 12.0, 1.0}), new NDPoint280(new Double[]{18.0, 1.0, 2.0}),
                new NDPoint280(new Double[]{2.0, 12.0, 16.0}), new NDPoint280(new Double[]{7.0, 3.0, 3.0}),
                new NDPoint280(new Double[]{3.0, 7.0, 5.0}), new NDPoint280(new Double[]{16.0, 4.0, 4.0}),
                new NDPoint280(new Double[]{4.0, 6.0, 1.0}), new NDPoint280(new Double[]{5.0, 5.0, 17.0})
        };
        KDTree280 d3Tree = new KDTree280(points, 3);
        d3Tree.toStringByLevel();
        System.out.println("Looking for points between (0.0 , 1.0 , 0.0) and (4.0 , 6.0 , 3.0). Found : ");
        ArrayList<NDPoint280> ndPoint280s = d3Tree.rangeSearches(new NDPoint280(new Double[]{0.0, 1.0, 0.0}), new NDPoint280(new Double[]{4.0, 6.0, 3.0}));
        for (NDPoint280 ndPoint280 : ndPoint280s) {
            System.out.println(ndPoint280.toString());
        }
        System.out.println("Looking for points between (0.0 , 1.0 , 0.0) and (8.0 , 7.0 , 4.0). Found :  ");
        ndPoint280s = d3Tree.rangeSearches(new NDPoint280(new Double[]{0.0, 1.0, 0.0}), new NDPoint280(new Double[]{8.0, 7.0, 4.0}));
        for (NDPoint280 ndPoint280 : ndPoint280s) {
            System.out.println(ndPoint280.toString());
        }
        System.out.println("Looking for points between (0.0 , 1.0 , 0.0) and (17.0 , 9.0 , 10.0). Found :  ");
        ndPoint280s = d3Tree.rangeSearches(new NDPoint280(new Double[]{0.0, 1.0, 0.0}), new NDPoint280(new Double[]{17.0, 9.0, 10.0}));
        for (NDPoint280 ndPoint280 : ndPoint280s) {
            System.out.println(ndPoint280.toString());
        }
    }
}
