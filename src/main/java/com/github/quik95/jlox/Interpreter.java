package com.github.quik95.jlox;

public class Interpreter implements Expr.Visitor<Object> {
    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        var left = evaluate(expr.left);
        var right = evaluate(expr.right);

        return switch (expr.operator.type) {
            case MINUS -> {
                checkNumberOperand(expr.operator, right);
                yield (double) left - (double) right;
            }
            case SLASH -> {
                yield (double) left / (double) right;
            }
            case STAR -> {
                yield (double) left * (double) right;
            }
            case GREATER -> {
                checkNumberOperand(expr.operator, left, right);
                yield (double) left > (double) right;
            }
            case GREATER_EQUAL -> {
                checkNumberOperand(expr.operator, left, right);
                yield (double) left >= (double) right;
            }
            case LESS -> {
                checkNumberOperand(expr.operator, left, right);
                yield (double) left < (double) right;
            }
            case LESS_EQUAL -> {
                checkNumberOperand(expr.operator, left, right);
                yield (double) left <= (double) right;
            }
            case BANG_EQUAL -> {
                checkNumberOperand(expr.operator, left, right);
                yield !isEqual(left, right);
            }
            case EQUAL_EQUAL -> {
                checkNumberOperand(expr.operator, left, right);
                yield isEqual(left, right);
            }
            case PLUS -> {
                if (left instanceof Double && right instanceof Double) {
                    yield (double) left + (double) right;
                }
                if (left instanceof String && right instanceof String) {
                    yield left + (String) right;
                }
                throw new RuntimeError(expr.operator, "Operands must be two numbers or two strings.");
            }
            default -> null;
        };
    }

    void interpret(Expr expression) {
        try {
            var value = evaluate(expression);
            System.out.println(stringify(value));
        } catch (RuntimeError error) {
            Main.runtimeError(error);
        }
    }

    private String stringify(Object object) {
        if (object == null) return "nil";

        if (object instanceof Double) {
            var text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }

        return object.toString();
    }

    private void checkNumberOperand(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;

        throw new RuntimeError(operator, "Operands must be numbers.");
    }

    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;

        return a.equals(b);
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }


    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        var right = evaluate(expr.right);

        return switch (expr.operator.type) {
            case BANG -> !isTruthy(right);
            case MINUS -> -(double) right;
            default -> null;
        };

    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean) object;
        return true;
    }
}
