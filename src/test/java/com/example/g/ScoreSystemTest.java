package com.example.g;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreSystemTest {

    @Test
    void CherriesTest() {
        ScoreSystem s = new ScoreSystem();
        s.setNoOfCherry(20);
        assertEquals(42, s.getCurrentScore());
    }

    @Test
    void setCurrentScore() {
        ScoreSystem s = new ScoreSystem();
        s.setCurrentScore(42);
        assertEquals(42, s.getCurrentScore());

    }

    void InitCherryTest(){
        ScoreSystem s = new ScoreSystem();
        s.setInitCherry(42);
        assertEquals(42, s.getInitCherry());
    }


}

