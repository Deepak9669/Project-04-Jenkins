package in.co.rays.proj4.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import in.co.rays.proj4.bean.DropdownListBean;
import in.co.rays.proj4.model.RoleModel;

/**
 * HTMLUtility is a helper class for generating common HTML UI components,
 * mainly &lt;select&gt; dropdowns from {@link HashMap} and {@link List}
 * of {@link DropdownListBean}.
 *
 * <p>Typical usage in JSP:
 * <pre>
 *   &lt;%= HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), roleList) %&gt;
 * </pre>
 *
 * This avoids writing repetitive dropdown HTML in JSP pages.
 *
 * @author Deepak Verma
 * @version 1.0
 */
public class HTMLUtility {

    /**
     * Creates an HTML &lt;select&gt; dropdown from a {@link HashMap}.
     *
     * @param name        name attribute of the select tag
     * @param selectedVal value to be pre-selected (can be null)
     * @param map         key-value pairs (key=option value, value=option label)
     * @return HTML string of select element
     */
    public static String getList(String name, String selectedVal, HashMap<String, String> map) {

        StringBuffer sb = new StringBuffer(
                "<select style=\"width: 169px;text-align-last: center;\" class='form-control' name='"
                        + name + "'>");

        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        Set<String> keys = map.keySet();
        String val;

        for (String key : keys) {
            val = map.get(key);
            if (selectedVal != null && key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='").append(key).append("'>")
                        .append(val).append("</option>");
            } else {
                sb.append("\n<option value='").append(key).append("'>")
                        .append(val).append("</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Creates an HTML &lt;select&gt; dropdown from a {@link List}
     * of objects that implement {@link DropdownListBean}.
     *
     * @param name        name attribute of the select tag
     * @param selectedVal value to be pre-selected (can be null)
     * @param list        list of DropdownListBean objects
     * @return HTML string of select element
     */
    @SuppressWarnings("unchecked")
    public static String getList(String name, String selectedVal, List list) {

        List<DropdownListBean> dd = (List<DropdownListBean>) list;

        StringBuffer sb = new StringBuffer(
                "<select style=\"width: 169px;text-align-last: center;\" "
                        + "class='form-control' name='" + name + "'>");

        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        String key;
        String val;

        for (DropdownListBean obj : dd) {
            key = obj.getKey();
            val = obj.getValue();

            if (selectedVal != null && key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='").append(key).append("'>")
                        .append(val).append("</option>");
            } else {
                sb.append("\n<option value='").append(key).append("'>")
                        .append(val).append("</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Test method for getList(HashMap...) – prints generated HTML.
     */
    public static void testGetListByMap() {

        HashMap<String, String> map = new HashMap<>();
        map.put("male", "Male");
        map.put("female", "Female");

        String selectedValue = "male";
        String htmlSelectFromMap = HTMLUtility.getList("gender", selectedValue, map);

        System.out.println(htmlSelectFromMap);
    }

    /**
     * Test method for getList(List...) – prints generated HTML.
     *
     * @throws Exception if any error occurs in model.list()
     */
    public static void testGetListByList() throws Exception {

        RoleModel model = new RoleModel();

        List list = model.list();

        String selectedValue = "1";

        String htmlSelectFromList = HTMLUtility.getList("roleId", selectedValue, list);

        System.out.println(htmlSelectFromList);
    }

    public static void main(String[] args) throws Exception {

        testGetListByMap();

        // Uncomment to test list based dropdown
        // testGetListByList();
    }
}
