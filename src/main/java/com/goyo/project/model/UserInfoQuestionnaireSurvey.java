/*package com.goyo.project.model;

import javax.persistence.*;

import com.goyo.project.core.Comment;

import java.io.Serializable;
import java.util.Date;

*//**
 * @author: JinYue
 * @ClassName: UserInfoQuestionnaireSurvey 
 * @Description: 用户和问卷调查类
 *//*
@Table(name="USERINFO_QUESTIONNAIRESURVEY")
@Entity
public class UserInfoQuestionnaireSurvey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "USERINFO_ID")
    private UserInfo userInfo;

    @Id
    @ManyToOne
    @JoinColumn(name = "QUESTIONNAIRESURVEY_ID")
    private QuestionnaireSurvey questionnaireSurvey;

    @Column(name = "FINISH_DATE")
    private Date finishDate;
    
    @Comment("用户填写状态0代表未填写,1代表填写") 
    @Column(name="FILL_STATUS")
    private byte fillStatus;
    
    @Comment("用户填写得答案") //暂时不这么做了
    @Column(name="ANSWER")
    private AnswerProblem answerProblem;

//  题id/a：b：c	,
    public BookAuthor() {
        super();
    }

    public BookAuthor(Book book, Author author) {
        super();
        this.book = book;
        this.author = author;
    }

    public BookAuthor(Book book, Author author, Date finishDate) {
        super();
        this.book = book;
        this.author = author;
        this.finishDate = finishDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public String toString() {
        return String.format("BookAuthor [book=%s, author=%s, finishDate=%s]", book.getName(), author.getName(), finishDate);
    }

}*/