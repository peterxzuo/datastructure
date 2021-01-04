package com.peterzuo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.peterzuo.utils.WordDuplicateRemoval;

import org.junit.jupiter.api.Test;

public class WordDuplicateRemovalTests {
    @Test
    public void duplicateWord_Test(){
        String [] sentences = new String[]{
            "Hello hello Ab aB",
            "Goodbye bye bye world world world",
            "Sam went went to to to his business",
            "Reya is is the the best player in eye eye game",
            "in inthe"
        };

        String [] desired_sentences = new String[]{
            "Hello Ab",
            "Goodbye bye world",
            "Sam went to his business",
            "Reya is the best player in eye game",
            "in inthe",
        };

        for (int i=0; i<sentences.length; i++){
            String removedText = WordDuplicateRemoval.removeDuplicates(sentences[i]);
            assertEquals(desired_sentences[i], removedText);
        }
    }
}
