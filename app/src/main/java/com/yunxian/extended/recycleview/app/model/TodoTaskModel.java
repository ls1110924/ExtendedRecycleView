package com.yunxian.extended.recycleview.app.model;

/**
 * 主页中代办任务的数据模型
 *
 * @author A Shuai
 * @email ls1110924@163.com
 * @date 2016/9/13 20:18
 */
public class TodoTaskModel {

    private String deadline;
    private String title;
    private String content;
    private String action;

    public TodoTaskModel() {
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
