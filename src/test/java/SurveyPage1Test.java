import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SurveyPage1Test {

    public static final List<String> QUESTION_DESCRIPTIONS =
            List.of("Was anything disappointing about this product?\\r\\n",
                    "If this product was an animal, what kind would it be?");

    public static final String QUESTION_DESCRIPTION = "Was anything disappointing about this product?\r\n";
    public static final int QUESTION_NUM = 1;
    public static final int QUESTION_ID = 1;
    public static final String QUESTION_TYPE = "open ended";
    public static final int SURVEY_ID = 1;

    public static final String QUESTIONS_SELECT = "SELECT * FROM questions WHERE question_id = 1";
    public static final String SSN = "12345";

    public surveyPage1 sPage1;

    @Before
    public void setup() {
        sPage1 = new surveyPage1();
//

    }

    @Test
    public void whenGetQuestionsThenReturnListOfQuestionDescriptions() {
        sPage1.getQuestions();
        verify(sPage1).getQuestions();
    }

    @Test
    public void whenSelectQuestionByQuestionIdThenReturnQuestionInformation() throws SQLException {
        var conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
        var stmt = conn.createStatement();
        var resultSet = stmt.executeQuery(QUESTIONS_SELECT);
        assertTrue(resultSet.next());

        assertEquals(QUESTION_DESCRIPTION, resultSet.getString("Description"));
        assertEquals(QUESTION_NUM, resultSet.getInt("Question_num"));
        assertEquals(QUESTION_ID, resultSet.getInt("Question_id"));
        assertEquals(QUESTION_TYPE, resultSet.getString("Question_type"));
        assertEquals(SURVEY_ID, resultSet.getInt("Survey_id"));

        conn.close();
    }

    @Test
    public void whenCalculateResponseScoreThenReturnCalculatedScore() {
        assertEquals(12.0, sPage1.calculateResponseScore(3, 2), 0.001);
    }

}
