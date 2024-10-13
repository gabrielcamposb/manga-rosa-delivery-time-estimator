package br.com.mangarosa.collections;

import java.util.ArrayList;
import java.util.List;

// Classe que implementa a travessia em ordem de uma árvore binária.
// A travessia em ordem visita os nós na sequência: esquerda, raiz, direita
public class InOrderTraversal implements TreeTraversal<Integer> {

    // Percorre a árvore em ordem e retorna uma lista dos valores dos nós
    @Override
    public List<Integer> traverse(Tree<Integer> tree) {
        List<Integer> result = new ArrayList<>();
        inOrder(tree.root(), result);
        return result;
    }

    // Método auxiliar que realiza a travessia em ordem de forma recursiva
    private void inOrder(BinaryTreeNode<Integer> node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeftChild(), result);
        result.add(node.getValue());
        inOrder(node.getRightChild(), result);
    }
}
