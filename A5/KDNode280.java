/**
 * Kristine Trinh
 * nlt895
 * 11190412
 */
package lib280.tree;

import lib280.base.NDPoint280;

public class KDNode280 {

    private NDPoint280 key;
    private KDNode280 left;
    private KDNode280 right;

    /**
     * Create a node from a ndPoint
     * @param key ndPoint
     */
    public KDNode280(NDPoint280 key) {
        this.key = key;
        left = null;
        right = null;
    }

    public NDPoint280 getKey() {
        return key;
    }

    public KDNode280 getLeft() {
        return left;
    }

    public void setLeft(KDNode280 left) {
        this.left = left;
    }

    public KDNode280 getRight() {
        return right;
    }

    public void setRight(KDNode280 right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return key.toString();
    }
}
