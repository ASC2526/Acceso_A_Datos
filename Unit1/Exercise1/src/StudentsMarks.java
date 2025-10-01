import java.util.*;

public class Student
{
    private String _name;
    private int _mark;
    public String GetName()
    {
        return _name;
    }
    public void SetName(String n)
    {
        _name = n;
    }
    public int GetMark()
    {
        return _mark;
    }
    public void SetMark(int m)
    {
        if (m >= 0 && m <= 10)
            _mark = m;
    }
    public Boolean Approved;
    {
        get
        {
            if (_mark >= 5)
                return true;
            else
                return false;
        }
    }
}

public class Students
{
    private ArrayList<Student> studentsList = new ArrayList();

    // Add a new student to the list
    //
    public void Add(Student stu)
    {
        studentsList.add(stu);
    }

    // Return the student that is in the "num" position
    //
    public Student GetStudent(int num)
    {
        if (num >= 0 && num <= studentsList.Count)
        {
            return ((Student)studentsList[num]);
        }
        return null;
    }

    // Return the student´s average mark
    //
    public float GetAverage;
    {
        get
        {
            if (studentsList.Count == 0)
                return 0;
            else
            {
                float average = 0;
                for (int i = 0; i < studentsList.Count; i++)
                {
                    average += ((Student)studentsList[i]).Mark;
                }
                return (average / studentsList.Count);
            }
        }
    }
}




