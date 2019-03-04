package cm.busime.camerpay.api;

import javax.enterprise.inject.Alternative;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/**
 * Convenience implementation around JsonBuilderFactory that eliminates the uncomfortable and error prone null checks
 * before a value can be added to a json object. The current implementation fully implements the standard json builder
 * interfaces and it's implementation delegates every call to the underlying default implementation.
 * <p/>
 * {@link #getConfigInUse()} is currently not implemented and can be adapted later if needed.
 * <p/>
 * Following methods implement an additional null check of the specified value, before the call is delegated to the
 * standard json builder implementations:
 * <pre>
 * {@link CustomJsonBuilderFactory.CustomJsonObjectBuilder#add(String, BigDecimal)}
 * {@link CustomJsonBuilderFactory.CustomJsonObjectBuilder#add(String, BigInteger)}
 * {@link CustomJsonBuilderFactory.CustomJsonObjectBuilder#add(String, String)}
 * {@link CustomJsonBuilderFactory.CustomJsonArrayBuilder#add(BigDecimal)}
 * {@link CustomJsonBuilderFactory.CustomJsonArrayBuilder#add(BigInteger)}
 * {@link CustomJsonBuilderFactory.CustomJsonArrayBuilder#add(String)}
 * </pre>
 */

@Alternative
public class CustomJsonBuilderFactory implements JsonBuilderFactory {

  private final JsonBuilderFactory jsonBuilderFactory;

  public CustomJsonBuilderFactory() {
    jsonBuilderFactory = Json.createBuilderFactory(null);
  }

  @Override
  public JsonObjectBuilder createObjectBuilder() {
    return new CustomJsonObjectBuilder(jsonBuilderFactory.createObjectBuilder());
  }

  @Override
  public JsonArrayBuilder createArrayBuilder() {
    return new CustomJsonArrayBuilder(jsonBuilderFactory.createArrayBuilder());
  }

  @Override
  public Map<String, ?> getConfigInUse() {
    throw new UnsupportedOperationException();
  }

  private static class CustomJsonObjectBuilder implements JsonObjectBuilder {

    private final JsonObjectBuilder jsonObjectBuilder;

    public CustomJsonObjectBuilder(final JsonObjectBuilder pJsonObjectBuilder) {
      this.jsonObjectBuilder = pJsonObjectBuilder;
    }

    @Override
    public JsonObjectBuilder add(final String name, final JsonValue value) {
      jsonObjectBuilder.add(name, value);
      return this;
    }

    @Override
    public JsonObjectBuilder add(final String name, final String value) {
      if (value != null) {
        jsonObjectBuilder.add(name, value);
      }
      return this;
    }

    @Override
    public JsonObjectBuilder add(final String name, final BigInteger value) {
      if (value != null) {
        jsonObjectBuilder.add(name, value);
      }
      return this;
    }

    @Override
    public JsonObjectBuilder add(final String name, final BigDecimal value) {
      if (value != null) {
        jsonObjectBuilder.add(name, value);
      }
      return this;
    }

    @Override
    public JsonObjectBuilder add(final String name, final int value) {
      jsonObjectBuilder.add(name, value);
      return this;
    }

    @Override
    public JsonObjectBuilder add(final String name, final long value) {
      jsonObjectBuilder.add(name, value);
      return this;
    }

    @Override
    public JsonObjectBuilder add(final String name, final double value) {
      jsonObjectBuilder.add(name, value);
      return this;
    }

    @Override
    public JsonObjectBuilder add(final String name, final boolean value) {
      jsonObjectBuilder.add(name, value);
      return this;
    }

    @Override
    public JsonObjectBuilder addNull(final String name) {
      jsonObjectBuilder.addNull(name);
      return this;
    }

    @Override
    public JsonObjectBuilder add(final String name, final JsonObjectBuilder builder) {
      jsonObjectBuilder.add(name, builder);
      return this;
    }

    @Override
    public JsonObjectBuilder add(final String name, final JsonArrayBuilder builder) {
      jsonObjectBuilder.add(name, builder);
      return this;
    }

    @Override
    public JsonObject build() {
      return jsonObjectBuilder.build();
    }
  }

  private static class CustomJsonArrayBuilder implements JsonArrayBuilder {

    private final JsonArrayBuilder jsonArrayBuilder;

    public CustomJsonArrayBuilder(final JsonArrayBuilder pJsonArrayBuilder) {
      this.jsonArrayBuilder = pJsonArrayBuilder;
    }

    @Override
    public JsonArrayBuilder add(final JsonValue value) {
      jsonArrayBuilder.add(value);
      return this;
    }

    @Override
    public JsonArrayBuilder add(final String value) {
      if (value != null) {
        jsonArrayBuilder.add(value);
      }
      return this;
    }

    @Override
    public JsonArrayBuilder add(final BigDecimal value) {
      if (value != null) {
        jsonArrayBuilder.add(value);
      }
      return this;
    }

    @Override
    public JsonArrayBuilder add(final BigInteger value) {
      if (value != null) {
        jsonArrayBuilder.add(value);
      }
      return this;
    }

    @Override
    public JsonArrayBuilder add(final int value) {
      jsonArrayBuilder.add(value);
      return this;
    }

    @Override
    public JsonArrayBuilder add(final long value) {
      jsonArrayBuilder.add(value);
      return this;
    }

    @Override
    public JsonArrayBuilder add(final double value) {
      jsonArrayBuilder.add(value);
      return this;
    }

    @Override
    public JsonArrayBuilder add(final boolean value) {
      jsonArrayBuilder.add(value);
      return this;
    }

    @Override
    public JsonArrayBuilder addNull() {
      jsonArrayBuilder.addNull();
      return this;
    }

    @Override
    public JsonArrayBuilder add(final JsonObjectBuilder builder) {
      jsonArrayBuilder.add(builder);
      return this;
    }

    @Override
    public JsonArrayBuilder add(final JsonArrayBuilder builder) {
      jsonArrayBuilder.add(builder);
      return this;
    }

    @Override
    public JsonArray build() {
      return jsonArrayBuilder.build();
    }
  }
}
