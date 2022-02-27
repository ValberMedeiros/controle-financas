package br.com.dev.valber.medeiros.controleficancas.domain.response;

public class ObjectDataResponse<T> {


    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ObjectDataResponse<T> build(T response) {
        ObjectDataResponse<T> objectDataResponse = new ObjectDataResponse<>();
        objectDataResponse.setData(response);
        return objectDataResponse;
    }

    @Override
    public String toString() {
        return "ObjectDataResponse [data=" + data + "]";
    }
}
