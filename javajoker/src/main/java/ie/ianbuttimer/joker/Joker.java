/*
 * Copyright (c) 2017 Ian Buttimer.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ie.ianbuttimer.joker;

public class Joker {

    /* jokes courtesy of Comedy Central, http://jokes.cc.com/ */
    private static final String[] mJokes = new String[] {
        "A bear walks into a bar and says to the bartender, \"I'll have a pint of beer and a.......... packet of peanuts.\"\n" +
            "The bartender asks, \"Why the big pause?\"",
        "A grasshopper walks into a bar, and the bartender says, \"Hey, we have a drink named after you!\"" +
            "The grasshopper looks surprised and asks, \"You have a drink named Steve?\"",
            "A man and his pet giraffe walk into a bar and start drinking. As the night goes on, they get drunk, and the giraffe finally passes out. The man decides to go home.\n" +
        "As he's leaving, the man is approached by the barkeeper who says, \"Hey, you're not gonna leave that lyin' here, are ya?\"\n" +
            "\"Hmph,\" says the man. \"That's not a lion -- it's a giraffe.\"",
        "A dog walks into a bar and hops up on a stool. He looks the bartender in the eye and says, \"Hey, guess what? I can talk. Have you ever seen a talking dog before? How about a drink?\"\n" +
                "\n" +
                "The bartender thinks for a moment and says, \"Sure, the toilet's right around the corner.\""
    };

    private int mIndex;

    /**
     * Default constructor
     */
    public Joker() {
        mIndex = 0;
    }

    public String getAJoke() {
        String joke = mJokes[mIndex];
        ++mIndex;
        if (mIndex == mJokes.length) {
            mIndex = 0;
        }
        return joke;
    }
}
