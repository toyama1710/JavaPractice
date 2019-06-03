
class UnionFind {
    private int[] tree;

    public UnionFind(int size) {
        tree = new int[size];

        for (int i = 0; i < size; i++) {
            tree[i] = -1;
        }
    }

    public int root(int node) {
        if (tree[node] < 0) {
            return node;
        }
        else {
            return tree[node] = root(tree[node]);
        }
    }

    public boolean same(int x, int y) {
        return root(x) == root(y);
    }

    public void unite(int x, int y) {
        x = root(x);
        y = root(y);

        if (x == y) {
            return;
        }
        else {
            tree[x] += tree[y];
            tree[y] = x;

            return;
        }
    }

    public int size(int node) {
        return -1 * root(node);
    }
}
