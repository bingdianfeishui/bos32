package com.itheima.bos.web.test;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JacksonTest {

    /**
     * jackson View test
     * 
     * @throws JsonProcessingException
     */
    @Test
    public void test01() throws JsonProcessingException {
        class Test01Views {
            class PublicView {
            }

            class StreamA extends PublicView {
            }

            class OtherWay extends PublicView {
            }
        }

        class Test01Value {
            @JsonView(Test01Views.PublicView.class)
            public int value1;
            @JsonView(Test01Views.OtherWay.class)
            public int value2;
            @JsonView(Test01Views.StreamA.class)
            public int value3;
        }

        Test01Value valueInstance = new Test01Value();
        valueInstance.value1 = 100;
        valueInstance.value2 = 200;
        valueInstance.value3 = 300;

        String json1 = new ObjectMapper().writerWithView(
                Test01Views.PublicView.class).writeValueAsString(valueInstance);
        String json2 = new ObjectMapper().writerWithView(
                Test01Views.OtherWay.class).writeValueAsString(valueInstance);
        String json3 = new ObjectMapper().writerWithView(
                Test01Views.StreamA.class).writeValueAsString(valueInstance);
        System.out.println(json1);
        System.out.println(json2);
        System.out.println(json3);
        System.out.println();
    }

    @JsonFilter("filterAClass")
    class Test02AClass {
        public String id = "A";
        public String name = "Fred";
        public String color = "blue";
        public int sal = 56;
        public Test02BClass bclass = new Test02BClass();
    }

    // @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @JsonFilter("filterBClass")
    class Test02BClass {

        public String id = "B";
        public String size = "90";
        public String height = "tall";
        public String nulcheck = null;
    }

    /**
     * Filter: dynamic ignore some properties
     * 
     * @throws JsonProcessingException
     */
    @Test
    public void test02() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Exclude Null Fields
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        FilterProvider filters = new SimpleFilterProvider().addFilter(
                "filterAClass",
                SimpleBeanPropertyFilter.filterOutAllExcept(new String[] {
                        "id", "color", "bclass" })).addFilter(
                "filterBClass",
                SimpleBeanPropertyFilter
                        .serializeAllExcept(new String[] { "height" }));
        ObjectWriter writer = mapper.writer(filters);
        System.out.println(writer.writeValueAsString(new Test02AClass()));
        System.out.println();
    }

    class Test03POJO {
        private String a;
        private String b;
        private String c;
        private String d;
        
        public String getA() {
            return a;
        }
        public void setA(String a) {
            this.a = a;
        }
        public String getB() {
            return b;
        }
        public void setB(String b) {
            this.b = b;
        }
        public String getC() {
            return c;
        }
        public void setC(String c) {
            this.c = c;
        }
        public String getD() {
            return d;
        }
        public void setD(String d) {
            this.d = d;
        }

        public Test03POJO(String a, String b, String c, String d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    @JsonFilter("test03Filter")
    interface Test03FilterMinIn {
    }

    /**
     * MinIn filter: dynamic ignore some properties MixIn混入注解：不修改POJO，采取后期代码混入方式
     * 
     * @throws JsonProcessingException
     */
    @Test
    public void test03() throws JsonProcessingException {
        Test03POJO pojo = new Test03POJO("a", "b", "c", "d");
        ObjectMapper objectMapper = new ObjectMapper();
        // FilterProvider filters = new SimpleFilterProvider().addFilter(
        // "test03Filter",
        // SimpleBeanPropertyFilter.filterOutAllExcept("a", "b"));
        // objectMapper.setFilterProvider(filters);
        // objectMapper.addMixIn(Test03POJO.class, Test03FilterMinIn.class);
        // String jsonStr = objectMapper.writeValueAsString(pojo);
        // System.out.println(jsonStr);

        FilterProvider filters = new SimpleFilterProvider().addFilter(
                "test03Filter2",
                SimpleBeanPropertyFilter.filterOutAllExcept("b", "c"));
        objectMapper.setFilterProvider(filters);
        objectMapper.addMixIn(Test03POJO.class, Test03FilterMinIn.class);
        String jsonStr = objectMapper.writeValueAsString(pojo);
        System.out.println(jsonStr);
        System.out.println();
    }
    
    @JsonIgnoreProperties({"c","d"})
    abstract class Test04MixIn{
        Test04MixIn(@JsonProperty("a")String a){}
        @JsonProperty("ValueB")
        abstract String getB();
        //@JsonProperty("ValueD") //若不注释此句，则会覆盖@JsonIgnoreProperties中的"d"效果，始终有valueD值
        abstract String getD();
        @JsonIgnore
        abstract String getA();
    }
    
    /**
     * 纯MixIn方式进行属性控制
     * @throws JsonProcessingException
     */
    @Test
    public void test04() throws JsonProcessingException{
        Test03POJO pojo = new Test03POJO("a", "b", "c", "d");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Test03POJO.class, Test04MixIn.class);
        String jsonStr = objectMapper.writeValueAsString(pojo);
        System.out.println(jsonStr);
        System.out.println();
    }
}
