package com.upd.common.util.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Prop {
    private Properties properties;

    public Prop(String fileName) {
        this(fileName, "UTF-8");
    }

    public Prop(String fileName, String encoding) {
        this.properties = null;
        InputStream inputStream = null;

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(inputStream == null) {
                throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
            }

            this.properties = new Properties();
            this.properties.load(new InputStreamReader(inputStream, encoding));
        } catch (IOException var12) {
            throw new RuntimeException("Error loading properties file.", var12);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public Prop(File file) {
        this(file, "UTF-8");
    }

    public Prop(File file, String encoding) {
        this.properties = null;
        if(file == null) {
            throw new IllegalArgumentException("File can not be null.");
        } else if(!file.isFile()) {
            throw new IllegalArgumentException("File not found : " + file.getName());
        } else {
            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream(file);
                this.properties = new Properties();
                this.properties.load(new InputStreamReader(inputStream, encoding));
            } catch (IOException var12) {
                throw new RuntimeException("Error loading properties file.", var12);
            } finally {
                if(inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }

    public String get(String key) {
        return this.properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }

    public Integer getInt(String key) {
        return this.getInt(key, (Integer)null);
    }

    public Integer getInt(String key, Integer defaultValue) {
        String value = this.properties.getProperty(key);
        return value != null?Integer.valueOf(Integer.parseInt(value.trim())):defaultValue;
    }

    public Long getLong(String key) {
        return this.getLong(key, (Long)null);
    }

    public Long getLong(String key, Long defaultValue) {
        String value = this.properties.getProperty(key);
        return value != null?Long.valueOf(Long.parseLong(value.trim())):defaultValue;
    }

    public Boolean getBoolean(String key) {
        return this.getBoolean(key, (Boolean)null);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = this.properties.getProperty(key);
        if(value != null) {
            value = value.toLowerCase().trim();
            if("true".equals(value)) {
                return Boolean.valueOf(true);
            } else if("false".equals(value)) {
                return Boolean.valueOf(false);
            } else {
                throw new RuntimeException("The value can not parse to Boolean : " + value);
            }
        } else {
            return defaultValue;
        }
    }

    public boolean containsKey(String key) {
        return this.properties.containsKey(key);
    }

    public Properties getProperties() {
        return this.properties;
    }
}
