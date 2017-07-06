package hr.fer.android.hw0036487720.model;

import java.util.function.BiFunction;

/**
 * Class used as an encapsulation for {@link BiFunction} and its name.
 */
public class Operation {

    /**
     * Name of the operation
     */
    private String operationName;

    /**
     * Actual operation
     */
    private BiFunction<Double, Double, Double> operation;


    /**
     * Instantiates a new operation from provided elements.
     *
     * @param operationName the operation name
     * @param operation     the operation
     */
    public Operation(String operationName, BiFunction<Double, Double, Double> operation) {
        this.operationName = operationName;
        this.operation = operation;
    }

    /**
     * Gets operation name.
     *
     * @return the operation name
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * Gets operation.
     *
     * @return the operation
     */
    public BiFunction<Double, Double, Double> getOperation() {
        return operation;
    }
}
