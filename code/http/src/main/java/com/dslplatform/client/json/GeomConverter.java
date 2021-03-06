package com.dslplatform.client.json;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

public class GeomConverter {
	public static void serializeLocationNullable(final Point2D value, final Writer sw) throws IOException {
		if (value == null) {
			sw.write("null");
		} else {
			serializeLocation(value, sw);
		}
	}

	public static void serializeLocation(final Point2D value, final Writer sw) throws IOException {
		sw.write("{\"X\":");
		sw.write(Double.toString(value.getX()));
		sw.write(",\"Y\":");
		sw.write(Double.toString(value.getY()));
		sw.write("}");
	}

	public static Point2D deserializeLocation(final JsonReader reader) throws IOException {
		return null;
	}

	private static JsonReader.ReadObject<Point2D> LocationReader = new JsonReader.ReadObject<Point2D>() {
		@Override
		public Point2D read(JsonReader reader) throws IOException {
			return deserializeLocation(reader);
		}
	};

	public static ArrayList<Point2D> deserializeLocationCollection(final JsonReader reader) throws IOException {
		return reader.deserializeCollection(LocationReader);
	}

	public static void deserializeLocationCollection(final JsonReader reader, final Collection<Point2D> res) throws IOException {
		reader.deserializeCollection(LocationReader, res);
	}

	public static ArrayList<Point2D> deserializeLocationNullableCollection(final JsonReader reader) throws IOException {
		return reader.deserializeNullableCollection(LocationReader);
	}

	public static void deserializeLocationNullableCollection(final JsonReader reader, final Collection<Point2D> res) throws IOException {
		reader.deserializeNullableCollection(LocationReader, res);
	}

	public static void serializePointNullable(final Point value, final Writer sw) throws IOException {
		if (value == null) {
			sw.write("null");
		} else {
			serializePoint(value, sw);
		}
	}

	public static void serializePoint(final Point value, final Writer sw) throws IOException {
		sw.write("{\"X\":");
		sw.write(Integer.toString(value.x));
		sw.write(",\"Y\":");
		sw.write(Integer.toString(value.y));
		sw.write("}");
	}

	public static Point deserializePoint(final JsonReader reader) throws IOException {
		return null;
	}

	private static JsonReader.ReadObject<Point> PointReader = new JsonReader.ReadObject<Point>() {
		@Override
		public Point read(JsonReader reader) throws IOException {
			return deserializePoint(reader);
		}
	};

	public static ArrayList<Point> deserializePointCollection(final JsonReader reader) throws IOException {
		return reader.deserializeCollection(PointReader);
	}

	public static void deserializePointCollection(final JsonReader reader, final Collection<Point> res) throws IOException {
		reader.deserializeCollection(PointReader, res);
	}

	public static ArrayList<Point> deserializePointNullableCollection(final JsonReader reader) throws IOException {
		return reader.deserializeNullableCollection(PointReader);
	}

	public static void deserializePointNullableCollection(final JsonReader reader, final Collection<Point> res) throws IOException {
		reader.deserializeNullableCollection(PointReader, res);
	}

	public static void serializeRectangleNullable(final Rectangle2D value, final Writer sw) throws IOException {
		if (value == null) {
			sw.write("null");
		} else {
			serializeRectangle(value, sw);
		}
	}

	public static void serializeRectangle(final Rectangle2D value, final Writer sw) throws IOException {
		sw.write("{\"X\":");
		sw.write(Double.toString(value.getX()));
		sw.write(",\"Y\":");
		sw.write(Double.toString(value.getY()));
		sw.write(",\"Width\":");
		sw.write(Double.toString(value.getWidth()));
		sw.write(",\"Height\":");
		sw.write(Double.toString(value.getHeight()));
		sw.write("}");
	}

	public static Rectangle2D deserializeRectangle(final JsonReader reader) throws IOException {
		return null;
	}

	private static JsonReader.ReadObject<Rectangle2D> RectangleReader = new JsonReader.ReadObject<Rectangle2D>() {
		@Override
		public Rectangle2D read(JsonReader reader) throws IOException {
			return deserializeRectangle(reader);
		}
	};

	public static ArrayList<Rectangle2D> deserializeRectangleCollection(final JsonReader reader) throws IOException {
		return reader.deserializeCollection(RectangleReader);
	}

	public static void deserializeRectangleCollection(final JsonReader reader, final Collection<Rectangle2D> res) throws IOException {
		reader.deserializeCollection(RectangleReader, res);
	}

	public static ArrayList<Rectangle2D> deserializeRectangleNullableCollection(final JsonReader reader) throws IOException {
		return reader.deserializeNullableCollection(RectangleReader);
	}

	public static void deserializeRectangleNullableCollection(final JsonReader reader, final Collection<Rectangle2D> res) throws IOException {
		reader.deserializeNullableCollection(RectangleReader, res);
	}
}
