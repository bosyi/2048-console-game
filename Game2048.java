import java.util.Arrays;

public class Game2048 {
    public static void main(String[] args) {
        Game2048 game2048 = new Game2048();

        int[] array = {2,0,0,2};
        game2048.print(array);
        game2048.shiftRegular(array);
        game2048.print(array);
    }

    private void cycleBackward(int[] array, int from, int to) {
        for (; from < to; from++) {
            swipe(array, from, from + 1);
        }
    }

    private void shiftRegular(int[] array) {
        int to = array.length - 1;
        for (int from = 0; from < to;) {
            if (array[from] == 0) {
                cycleBackward(array, from, to);
                to--;
            } else if (array[from + 1] == 0) {
                cycleBackward(array, from + 1, to);
                to--;
            } else if (array[from] == array[from + 1]) {
                array[from] *= 2;
                array[from + 1] = 0;
                from++;
            } else {
                from++;
            }
        }
    }

    private void swipe(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void print(int[] array) {
        System.out.println(Arrays.toString(array));
    }
}
