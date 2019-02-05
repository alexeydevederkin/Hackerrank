package hackerrank;

import java.util.*;

/*
HackerLand National Bank has a simple policy for warning clients about possible fraudulent account activity.
If the amount spent by a client on a particular day is greater than or equal to 2x the client's
median spending for a trailing number of days, they send the client a notification about potential fraud.
The bank doesn't send the client any notifications until they have at least that
trailing number of prior days' transaction data.

Given the number of trailing days  and a client's total daily expenditures for a period of  days,
find and print the number of times the client will receive a notification over all n days.

For example, d = 3 and expenditures = [10, 20, 30, 40, 50]. On the first three days, they just collect spending data.
At day 4, we have trailing expenditures of [10, 20, 30]. The median is 20 and the day's expenditure is 40.
Because 40 >= 2 x 20, there will be a notice. The next day, our trailing expenditures are [20, 30, 40]
and the expenditures are 50. This is less than 2 x 30 so no notice will be sent.
Over the period, there was one notice sent.

0 <= expenditure <= 200
*/

public class FraudulentActivityNotifications {

    /*
     *  Transforming array [1, 3, 3, 4, 5, 3, 3] and finding median by scanning and counting:
     *  size = 7
     *  [0] = 0
     *  [1] = 1
     *  [2] = 0
     *  [3] = 4  <- counted 4 (> 7/2), then median = index = 3
     *  [4] = 1
     *  [5] = 1
     */
    private static class MedianFinder {
        private static final int MAX_VALUE = 200;
        private int size = 0;
        private int[] values = new int[MAX_VALUE];

        MedianFinder(int[] initialArray) {
            size = initialArray.length;
            for (int val : initialArray) {
                values[val]++;
            }
        }

        void removeValue(int value) {
            values[value]--;
            size--;
        }

        void addValue(int value) {
            values[value]++;
            size++;
        }

        double getMedian() {
            double median = 0;

            boolean sizeIsEven = size % 2 == 0;
            int half = size / 2;
            int counter = 0;

            for (int i = 0; i < values.length; i++) {
                counter += values[i];

                if (counter == half && sizeIsEven) {
                    // [0->0, 1->0, 2->1, 3->0, 4->1, 5->0] case: median = (2 + 4) / 2
                    double medianLeft = i;

                    int j = i + 1;
                    while (values[j] == 0) {
                        j++;
                    }

                    double medianRight = j;

                    median = (medianLeft + medianRight) / 2;
                    break;

                } else if (counter > half) {
                    // [0->0, 1->0, 2->1, 3->1, 4->1, 5->0] case: median = 3
                    median = i;
                    break;
                }
            }

            return median;
        }
    }

    private static int activityNotifications(int[] expenditure, int d) {
        if (d >= expenditure.length) {
            return 0;
        }

        int notifications = 0;

        MedianFinder medianFinder = new MedianFinder(Arrays.copyOfRange(expenditure, 0, d));

        for (int i = d; i < expenditure.length; i++) {

            double currentSpending = expenditure[i];

            if (i > d) {
                medianFinder.removeValue(expenditure[i - d - 1]);
                medianFinder.addValue(expenditure[i - 1]);
            }

            double medianPreviousSpending = medianFinder.getMedian();

            if (currentSpending >= 2 * medianPreviousSpending) {
                notifications++;
            }
        }

        return notifications;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int d = in.nextInt();
        int[] expenditure = new int[n];
        for(int expenditure_i = 0; expenditure_i < n; expenditure_i++){
            expenditure[expenditure_i] = in.nextInt();
        }
        int result = activityNotifications(expenditure, d);
        System.out.println(result);
        in.close();
    }
}
