import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static void addToMap(int letter_R_Count) {
        synchronized (sizeToFreq) {
            if (sizeToFreq.containsKey(letter_R_Count)) {
                sizeToFreq.put(letter_R_Count, sizeToFreq.get(letter_R_Count) + 1);
            } else {
                sizeToFreq.put(letter_R_Count, 1);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) { //1000
            Thread bigThread = new Thread(() -> {
                int letter_R_Count = 0;
                String route = generateRoute("RLRFR", 100);
                for (int j = 0; j < route.length(); j++) {
                    if (route.charAt(j) == 'R') {
                        letter_R_Count++;
                    }
                }
                addToMap(letter_R_Count);
            });
            bigThread.start();
        }

        int[] maxFreq = {0, 0};

        for (Map.Entry<Integer, Integer> item : sizeToFreq.entrySet()) {
            if (item.getValue() > maxFreq[1]) {
                maxFreq[0] = item.getKey();
                maxFreq[1] = item.getValue();
            }
        }

        System.out.println("Самое частое количество посторений " + maxFreq[0] + " (встретилось " + maxFreq[1] + " раз)");
        System.out.println("Другие размеры:");

        for (Map.Entry<Integer, Integer> item : sizeToFreq.entrySet()) {
            System.out.printf("- %d (%s раз) \n", item.getKey(), item.getValue());
        }
   }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

}