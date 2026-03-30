package model;

public class Student {
    private int rollNo;
    private String name;
    private double gpa;
    private String course;

    public Student(int rollNo, String name, double gpa, String course) {
        this.rollNo = rollNo;
        this.name   = name;
        this.gpa    = gpa;
        this.course = course;
    }

    public int    getRollNo() { return rollNo; }
    public String getName()   { return name; }
    public double getGpa()    { return gpa; }
    public String getCourse() { return course; }

    public void setGpa(double gpa)       { this.gpa = gpa; }
    public void setName(String name)     { this.name = name; }
    public void setCourse(String course) { this.course = course; }

    @Override
    public String toString() {
        return String.format("Roll#%-5d | %-20s | GPA: %.2f | Course: %s",
                rollNo, name, gpa, course);
    }
}
