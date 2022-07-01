package ua.in.asilichenko.prisonersriddle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Conditions:
 * <ul>
 * <li>100 prisoners numbered 1-100</li>
 * <li>Slips with their numbers are randomly placed in 100 boxes in a room</li>
 * <li>Each prisoner may enter the room one at a time and check 50 boxes</li>
 * <li>They must leave the room exactly as they found it and can't
 * communicate with the other after</li>
 * <li>If all 100 prisoners find their number during their turn in the room,
 * they will all be freed. But if even one fails, they will be executed.</li>
 * </ul>
 * <p>
 * What is their best strategy?
 * <p>
 * Inspired by Veritasium's video:<br/>
 * <a href="https://www.youtube.com/watch?v=iSNsgj1OCLA">
 * "The Riddle That Seems Impossible Even If You Know The Answer"
 * </a>
 * <p>
 * Creation date: 01.07.2022
 *
 * @author Alexey Silichenko
 */
public class PrisonersRiddle {

    private static final Random rnd = new Random(System.currentTimeMillis());

    private static final int ATTEMPTS_NUMBER = 100_000;

    private static final int PRISONERS_NUMBER = 1000;
    private static final int BOXES_ALLOWED_TO_OPEN = PRISONERS_NUMBER / 2;

    public static void main(String[] args) {
        final long successCnt = IntStream.range(0, ATTEMPTS_NUMBER).filter(i -> attempt()).count();
        final double percent = ((double) successCnt) / ATTEMPTS_NUMBER * 100;

        System.out.println(String.format("Succeeded %d of %d attempts. It's %.2f%%", successCnt, ATTEMPTS_NUMBER, percent));
    }

    private static boolean attempt() {
        final List<Integer> boxes = new ArrayList<>(PRISONERS_NUMBER);
        IntStream.range(0, PRISONERS_NUMBER).forEach(boxes::add);
        Collections.shuffle(boxes, rnd);

        return IntStream.range(0, PRISONERS_NUMBER).allMatch(prisonerNumber -> search(prisonerNumber, boxes));
    }

    private static boolean search(int prisonerNumber, List<Integer> boxes) {
        int boxNumber = prisonerNumber;
        for (int i = 0; i < BOXES_ALLOWED_TO_OPEN; i++) {
            final Integer slipNumber = boxes.get(boxNumber);
            if (slipNumber == prisonerNumber) return true;
            boxNumber = slipNumber;
        }
        return false;
    }
}
