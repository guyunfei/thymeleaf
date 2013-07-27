/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2012, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.standard.expression;


import org.thymeleaf.exceptions.TemplateProcessingException;

import java.lang.reflect.Method;

/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.1
 *
 */
public abstract class GreaterLesserExpression extends BinaryOperationExpression {


    private static final long serialVersionUID = 3488922833645278122L;
    
    
    protected static final String GREATER_THAN_OPERATOR = ">";
    protected static final String GREATER_OR_EQUAL_TO_OPERATOR = ">=";
    protected static final String LESS_THAN_OPERATOR = "<";
    protected static final String LESS_OR_EQUAL_TO_OPERATOR = "<=";
    protected static final String GREATER_THAN_OPERATOR_2 = "gt";
    protected static final String GREATER_OR_EQUAL_TO_OPERATOR_2 = "ge";
    protected static final String LESS_THAN_OPERATOR_2 = "lt";
    protected static final String LESS_OR_EQUAL_TO_OPERATOR_2 = "le";


    static final String[] OPERATORS = new String[] {
        GREATER_THAN_OPERATOR, GREATER_OR_EQUAL_TO_OPERATOR, LESS_THAN_OPERATOR, LESS_OR_EQUAL_TO_OPERATOR,
        GREATER_THAN_OPERATOR_2, GREATER_OR_EQUAL_TO_OPERATOR_2, LESS_THAN_OPERATOR_2, LESS_OR_EQUAL_TO_OPERATOR_2};
    private static final boolean[] LENIENCIES = new boolean[] { false, false, false, false, false, false, false, false };
    
    @SuppressWarnings("unchecked")
    private static final Class<? extends BinaryOperationExpression>[] OPERATOR_CLASSES = 
        (Class<? extends BinaryOperationExpression>[]) new Class<?>[] { 
            GreaterThanExpression.class, GreaterOrEqualToExpression.class,
            LessThanExpression.class, LessOrEqualToExpression.class,
            GreaterThanExpression.class, GreaterOrEqualToExpression.class,
            LessThanExpression.class, LessOrEqualToExpression.class};

    private static Method LEFT_ALLOWED_METHOD;
    private static Method RIGHT_ALLOWED_METHOD;


    static {
        try {
            LEFT_ALLOWED_METHOD = GreaterLesserExpression.class.getDeclaredMethod("isLeftAllowed", Expression.class);
            RIGHT_ALLOWED_METHOD = GreaterLesserExpression.class.getDeclaredMethod("isRightAllowed", Expression.class);
        } catch (final NoSuchMethodException e) {
            throw new TemplateProcessingException("Cannot register is*Allowed methods in binary operation expression", e);
        }
    }

    
    
    protected GreaterLesserExpression(final Expression left, final Expression right) {
        super(left, right);
    }




    static boolean isRightAllowed(final Expression right) {
        return right != null && !(right instanceof Token && !(right instanceof NumberTokenExpression));
    }

    static boolean isLeftAllowed(final Expression left) {
        return left != null && !(left instanceof Token && !(left instanceof NumberTokenExpression));
    }

    
    
    protected static ExpressionParsingState composeGreaterLesserExpression(
            final ExpressionParsingState state, int nodeIndex) {
        return composeBinaryOperationExpression(
                state, nodeIndex, OPERATORS, LENIENCIES, OPERATOR_CLASSES, LEFT_ALLOWED_METHOD, RIGHT_ALLOWED_METHOD);
    }

    
}
