package oop.ex6.scopes;

public class IfWhileScope extends Scope {

    public IfWhileScope(Scope father, int startLine, int endLine)
    {
        super(father, startLine, endLine);
    }

    @Override
    public boolean testScope() {
        return false;
    }
}
