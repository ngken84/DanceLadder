package ngke.casac.nstreet.model;

import ngke.casac.nstreet.model.template.DanceSubItem;
import ngke.casac.nstreet.model.template.SubItemContractTemplate;

public class Question extends DanceSubItem {

    public static final String TYPE = "QUESTION";

    private String Answer;
    private boolean answered;

    public static class Contract extends SubItemContractTemplate {

        public static String TABLE_NAME = "question_tbl";
        public static String COL_ANSWER = "answer";

        public static String getInitSQL() {
            return getCreateTableSQL(TABLE_NAME, COL_ANSWER + " TEXT, ");
        }

        public static String getDestroy() {
            return getDeleteTableSQL(TABLE_NAME);
        }

        public static String[] getProjection() {
            String[] projection = {
                    _ID,
                    COL_DANCE_ID,
                    COL_RATING,
                    COL_ORDER_NO,
                    COL_NAME,
                    COL_RATING,
                    COL_ANSWER,
                    COL_STARRED,
                    COL_DATE_CREATED,
                    COL_DATE_MODIFIED
            };
            return projection;
        }
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public String getQuestion() {
        return getName();
    }

    public void setQuestion(String question) {
        setName(question);
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
