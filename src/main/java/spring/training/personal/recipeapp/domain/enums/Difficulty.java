package spring.training.personal.recipeapp.domain.enums;

public enum Difficulty {
    EASY("Easy"),
    MODERATE("Moderate"),
    HARD("Hard");

    private final String difficultyCode;

    Difficulty(final String difficultyCode) {
        this.difficultyCode = difficultyCode;
    }

    public String getDifficultyCode() {
        return difficultyCode;
    }
}
