package com.dzb.math.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2021-03-14 15:36
 */
public class Csv<LINE>{
    private final String path;
    private final boolean hasHeader;

    private Function<String, LINE> func;

    public Csv(String path, Function<String, LINE> func, boolean hasHeader) {
        this.path = path;
        this.func = func;
        this.hasHeader = hasHeader;
    }

    public Csv(String path, Function<String, LINE> func) {
        this.path = path;
        this.func = func;
        this.hasHeader = false;
    }

    // 一个CSV对象的核心就是保存一个字符串的List(按行保存)
    private List<String> lines;
    private String header;

    public Csv(String path, Function<String, LINE> func, List<String> lines) {
        this.path = path;
        this.func = func;
        this.hasHeader = false;
        this.lines = lines;
    }

    public void setHeader(String... header) {
        this.header = String.join(",", header);
    }

    public void setFunc(Function<String, LINE> func) {
        this.func = func;
    }

    private synchronized List<String> load() {
        if (lines != null) {
            return lines;
        }

        lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String data;
            while (((data = br.readLine()) != null)) {
                lines.add(data);
            }
            br.close();
            if (hasHeader) {
                header = lines.remove(0);
            }
        } catch (IOException ignored) {
        }
        return lines;
    }

    public void save(String path) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(path));
            if (header != null && !"".equals(header)) {
                br.write(header);
                br.write('\n');
            }
            for (String line : lines) {
                br.write(line);
                br.write('\n');
            }
            br.close();
        } catch (IOException ignored) {
        }
    }

    public void save() {
        save(path);
    }

    // 使用时需要按照函数进行解析(并没有进行缓存优化)
    public List<LINE> asList() {
        return asList(func);
    }

    public List<LINE> asList(Function<String, LINE> func) {
        return load().stream().map(func).collect(Collectors.toList());
    }
}
