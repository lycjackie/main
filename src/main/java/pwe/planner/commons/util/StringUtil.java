package pwe.planner.commons.util;

import static java.util.Objects.requireNonNull;
import static pwe.planner.commons.util.AppUtil.checkArgument;
import static pwe.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns a string of all elements of the stream separated by commas.
     * If there are no elements in the stream, returns "None".
     * Syntactic sugar for {@code joinStreamAsString(stream, ", ")}
     * @param stream stream of elements to join
     */
    public static String joinStreamAsString(Stream<?> stream) {
        return joinStreamAsString(stream, ", ");
    }

    /**
     * Returns a string of all elements of the stream separated by delimiter.
     * If there are no elements in the stream, returns "None".
     * @param stream stream of elements to join
     * @param delimiter string used to separate the elements when joining them together as a string
     */
    public static String joinStreamAsString(Stream<?> stream, String delimiter) {
        requireAllNonNull(stream, delimiter);

        String joinnedString = stream.map(Object::toString).collect(Collectors.joining(delimiter));
        return (joinnedString.isEmpty()) ? "None" : joinnedString;
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireAllNonNull(sentence, word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Compare one string with another while ignoring cases and leading/trailing spaces.
     * @param stringA first string to compare
     * @param stringB second string to compare with
     * @return true if both strings are equal; false otherwise
     */
    public static boolean compareEqualsIgnoreCase(String stringA, String stringB) {
        requireAllNonNull(stringA, stringB);

        String preppedStringA = stringA.trim();
        checkArgument(!preppedStringA.isEmpty(), "stringA parameter cannot be empty");
        String preppedStringB = stringB.trim();
        checkArgument(!preppedStringB.isEmpty(), "stringB parameter cannot be empty");

        return preppedStringA.equalsIgnoreCase(preppedStringB);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);

        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
