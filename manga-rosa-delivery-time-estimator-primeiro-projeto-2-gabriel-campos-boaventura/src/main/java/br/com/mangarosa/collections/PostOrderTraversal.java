package br.com.mangarosa.collections;

import java.util.ArrayList;
import java.util.List;

// Classe que implementa a travessia em pós-ordem de uma árvore binária.
// A travessia em pós-ordem visita os nós na sequência: esquerda, direita, raiz
public class PostOrderTraversal implements TreeTraversal<Integer> {

    // Percorre a árvore em pós-ordem e retorna uma lista dos valores dos nós
    @Override
    public List<Integer> traverse(Tree<Integer> tree) {
        List<Integer> result = new ArrayList<>();
        postOrder(tree.root(), result);
        return result;
    }

    // Método auxiliar que realiza a travessia em pós-ordem de forma recursiva
    private void postOrder(BinaryTreeNode<Integer> node, List<Integer> result) {
        if (node == null) {
            return;
        }
        postOrder(node.getLeftChild(), result);
        postOrder(node.getRightChild(), result);
        result.add(node.getValue());
    }
}