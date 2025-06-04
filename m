@Bean
public ClassifierCompositeItemWriter<Object> classifierWriter() {
    return new ClassifierCompositeItemWriterBuilder<>()
        .classifier(item -> {
            if (item instanceof Customer) {
                return (ItemWriter<? super Object>) customerWriter();
            } else if (item instanceof Order) {
                return (ItemWriter<? super Object>) orderWriter();
            } else {
                throw new IllegalArgumentException("Unsupported type: " + item.getClass());
            }
        })
        .build();
}