import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Test {
    public static void main1(String[] args) {

        // 有 n 个学生站成一排，每个学生有一个能力值，牛牛想从这 n 个学生中按照顺序选取 k 名学生
        // 要求相邻两个学生的位置编号的差不超过 d，使得这 k 个学生的能力值的乘积最大
        // 你能返回最大的乘积吗？

        // 输入描述:
        // 每个输入包含 1 个测试用例。
        // 每个测试数据的第一行包含一个整数 n (1 <= n <= 50)，表示学生的个数
        // 接下来的一行，包含 n 个整数，按顺序表示每个学生的能力值 ai（-50 <= ai <= 50）
        // 接下来的一行包含两个整数，k 和 d (1 <= k <= 10, 1 <= d <= 50)。
        // 输出描述:
        // 输出一行表示最大的乘积。

        // 示例；
        // 输入
        // 3
        // 7 4 7
        // 2 50
        // 输出
        // 49

        // 36
        // 7 -15 31 49 -44 35 44 -47 -23 15 -11 10 -21 10 -13 0 -20 -36 22 -13 -39 -39 -31 -13 -27 -43 -6 40 5 -47 35 -8 24 -31 -24 -1
        // 3 31
        // 输出：108241 （49 * -47 * -47）

        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int[] power = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                power[i] = in.nextInt();
            }
            int k = in.nextInt();
            int d = in.nextInt();
            // 因为有正有负，负负得正，所以要维护两个 dp 数组，一个存储最大，一个存储最小。
            // 而不能使用 Arrays.sort() 进行升序排列
            long[][] fmax = new long[n + 1][k + 1]; // 记录最大乘积
            long[][] fmin = new long[n + 1][k + 1]; // 记录最小乘积
            // max[k][i]表示 : 从 n 个人中选择 k 个所产生的最大乘积；
            // min[k][i]表示 : 从 n 个人中选择 k 个所产生的最小乘积；
            // k = 1 时
            for (int one = 1; one <= n; one++) {
                fmax[one][1] = power[one];
                fmin[one][1] = power[one];
            }
            //自底向上递推
            for(int i = 2; i <= k; i++){
                for(int one = i; one <= n; one++){
                    //求解当 one 和 i 定的时候，最大的分割点
                    long tempmax = Long.MIN_VALUE;
                    long tempmin = Long.MAX_VALUE;
                    for(int left = Math.max(i - 1, one - d); left <= one - 1; left++){
                        if(tempmax < Math.max(fmax[left][i - 1] * power[one], fmin[left][i - 1] * power[one])){
                            tempmax = Math.max(fmax[left][i - 1] * power[one], fmin[left][i - 1] * power[one]);
                        }
                        if(tempmin > Math.min(fmax[left][i - 1] * power[one], fmin[left][i - 1] * power[one])) {
                            tempmin = Math.min(fmax[left][i - 1] * power[one], fmin[left][i - 1] * power[one]);
                        }
                    }
                    fmax[one][i] = tempmax;
                    fmin[one][i] = tempmin;
                }
            }
            // n 选 k 最大的需要从最后一个最大的位置选
            long result = Long.MIN_VALUE;
            for(int one = k; one <= n;one++){
                if(result < fmax[one][k]){
                    result = fmax[one][k];
                }
            }
            System.out.println(result);
        }
    }
}

public class Main {

    static class People {
        int height;
        int weight;

        public People(int weight, int height) {
            this.height = height;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {

        // 搜狐员工小王最近利用假期在外地旅游，在某个小镇碰到一个马戏团表演
        // 精彩的表演结束后发现团长正和大伙在帐篷前激烈讨论，小王打听了下了解到
        // 马戏团正打算出一个新节目“最高罗汉塔”, 即马戏团员叠罗汉表演。
        // 考虑到安全因素，要求叠罗汉过程中，站在某个人肩上的人应该既比自己矮又比自己瘦，或相等。
        // 团长想要本次节目中的罗汉塔叠的最高，由于人数众多，正在头疼如何安排人员的问题。
        // 小王觉得这个问题很简单，于是统计了参与最高罗汉塔表演的所有团员的身高体重，并且很快找到叠最高罗汉塔的人员序列。
        // 现在你手上也拿到了这样一份身高体重表，请找出可以叠出的最高罗汉塔的高度，这份表中马戏团员依次编号为 1 到 N。

        // 输入描述:
        // 首先一个正整数 N，表示人员个数。
        // 之后 N 行，每行三个数，分别对应马戏团员编号，体重和身高。
        // 输出描述:
        // 正整数m，表示罗汉塔的高度。

        // 示例:
        // 输入
        // 6
        // 1 65 100
        // 2 75 80
        // 3 80 100
        // 4 60 95
        // 5 82 101
        // 6 81 70
        // 输出
        // 4

        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            int n = scan.nextInt();
            People[] array = new People[n];
            for (int i = 0; i < n; ++i) {
                int index = scan.nextInt();
                array[index - 1] = new People(scan.nextInt(), scan.nextInt());
            }

            Arrays.sort(array, new Comparator<People>() {
                // 降序排列身高和体重
                public int compare(People p1, People p2) {
                    int result = Integer.compare(p1.height, p2.height);
                    if (result != 0) {
                        return result;
                    } else {
                        return Integer.compare(p1.weight, p2.weight);
                    }
                }
            });

            int[] dp = new int[n];
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < dp.length; ++i) {
                dp[i] = 1;
                for (int j = i - 1; j >= 0; --j) {
                    if (array[i].weight > array[j].weight || (array[i].weight == array[j].weight && array[i].height == array[j].height)) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
                max = Math.max(dp[i], max);
            }
            System.out.println(max);
        }
    }
}