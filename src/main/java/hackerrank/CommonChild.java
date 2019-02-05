package hackerrank;

import java.util.*;


/*
    A string is said to be a child of a another string if it can be formed by deleting 0 or more
    characters from the other string. Given two strings of equal length,
    what's the longest string that can be constructed such that it is a child of both?

    For example, ABCD and ABDC have two children with maximum length 3, ABC and ABD.
    They can be formed by eliminating either the D or C from both strings.
*/

public class CommonChild {

    private static int commonChild(String s1, String s2) {

        int[][] matrix = new int[s1.length()+1][s2.length()+1];

        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            for (int j = 0; j < s2.length(); j++) {
                if (c == s2.charAt(j)) {
                    matrix[i+1][j+1] = matrix[i][j] + 1;
                } else {
                    matrix[i+1][j+1] = Math.max(matrix[i+1][j], matrix[i][j+1]);
                }
            }
        }

        return matrix[s1.length()][s2.length()];
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s1 = in.next();
        String s2 = in.next();
        int result = commonChild(s1, s2);
        System.out.println(result);
    }
}
