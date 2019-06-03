/**
 * @author ei1710
 * @version 1.02
 */

//package toyama.ei1710.DataStructures;
import java.util.Arrays;

/**
 * 重み付きUnionFind木.
 * アーベル群まで拡張したいけどパフォが気になるので保留
 * ArrayList\<Integer\>がint[]よりだいぶ遅い
 * @see <a href="https://atcoder.jp/contests/arc090/submissions/5441773">検証1</a>
 * @see <a href="http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3579461#1">検証2</a>
 */
public class WeightedUnionFind {
    /** 親を示す 自身が根の場合は -(集合の大きさ) を持つ */
    private int[] parent;
    
    /** ノードの重み (親との差分) */
    private int[] diff_weight;

    public WeightedUnionFind(int nmemb) {
        parent = new int[nmemb];
        diff_weight = new int[nmemb];

        Arrays.fill(parent, -1);
        Arrays.fill(diff_weight, 0);
    }

    /** 根を求める */
    private int root(int x) {
        if (parent[x] < 0) {
            return x;
        }

        // 累積和的に重みを再計算
        int r = root(parent[x]);
        diff_weight[x] += diff_weight[parent[x]];
        parent[x] = r;

        return parent[x];
    }

    private int weight(int x) {
        root(x);
        return diff_weight[x];
    }

    public int diff(int x, int y) {
        return weight(y) - weight(x);
    }
    
    public boolean isSameGroup(int x, int y) {
        return root(x) == root(y);
    }

    /**
     * x->yの重みをwとして併合.
     * 即ち, weight(y) = weight(x) + w が成り立つ
     * x, yが既に同じグループのときは何もしない
     */
    public void unite(int x, int y, int w) {
        if (isSameGroup(x, y)) {
            return;
        }

        w += weight(x);
        w -= weight(y);
        x = root(x);
        y = root(y);
    
        parent[y] = x;
        diff_weight[y] = w;
    }

    public int groubSize(int x) {
        return -parent[root(x)];
    }
}
