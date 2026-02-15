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




