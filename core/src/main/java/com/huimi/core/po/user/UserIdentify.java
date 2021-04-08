package com.huimi.core.po.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.huimi.common.baseMapper.GenericPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


/**
 * 用户认证实体信息<br>
 *
 * @author fengting
 * @date 2018-04-24 02:41:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户认证")
@Table(name = "user_identify")
public class UserIdentify extends GenericPo<Integer> {

	public static final String TABLE_NAME = "user_identify";


	/**用户id**/
	@ApiModelProperty(value = "用户id")
	private Integer userId;
	/**实名状态 0,未认证:uncertified;1,待认证:waiting;2,已认证:certified;3,认证不通过:notPass**/
	@ApiModelProperty(value = "实名状态 0,未认证:uncertified;1,待认证:waiting;2,已认证:certified;3,认证不通过:notPass")
	private Integer realnameState;
	@Transient
	private String realnameStateFormatter ;
	/**证件正面图片(保存uuid.[文件格式])**/
	@ApiModelProperty(value = "证件正面图片(保存uuid.[文件格式])")
	private String idPositivePic;
	/**证件反面图片(保存uuid.[文件格式])**/
	@ApiModelProperty(value = "证件反面图片(保存uuid.[文件格式])")
	private String idNegativePic;
	/**实名审核时间**/
	@ApiModelProperty(value = "实名审核时间")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
	private Date realnameTime;
	/**邮箱状态 0,未认证:uncertified;1,待认证:waiting;2,已认证:certified;3,认证不通过:notPass**/
	@ApiModelProperty(value = "邮箱状态 0,未认证:uncertified;1,待认证:waiting;2,已认证:certified;3,认证不通过:notPass")
	private Integer emailState;
	@Transient
	private String emailStateFormatter ;
	/**邮箱审核时间**/
	@ApiModelProperty(value = "邮箱审核时间")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
	private Date emailTime;
	/**手机状态 0,未认证:uncertified;1,待认证:waiting;2,已认证:certified;3,认证不通过:notPass**/
	@ApiModelProperty(value = "手机状态 0,未认证:uncertified;1,待认证:waiting;2,已认证:certified;3,认证不通过:notPass")
	private Integer mobileState;
	@Transient
	private String mobileStateFormatter ;
	/**手机审核时间**/
	@ApiModelProperty(value = "手机审核时间")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
	private Date mobileTime;
	/**备注**/
	@ApiModelProperty(value = "备注")
	private String remark;
	public String getRealnameStateFormatter() {
		if(null == realnameStateFormatter || "".equals(realnameStateFormatter)){
			return REALNAMESTATE.getValue(getRealnameState());
		}
		return this.realnameStateFormatter;
	}
	public void setRealnameStateFormatter(String realnameStateFormatter) {
		this.realnameStateFormatter=realnameStateFormatter;
	}
	public String getEmailStateFormatter() {
		if(null == emailStateFormatter || "".equals(emailStateFormatter)){
			return EMAILSTATE.getValue(getEmailState());
		}
		return this.emailStateFormatter;
	}
	public void setEmailStateFormatter(String emailStateFormatter) {
		this.emailStateFormatter=emailStateFormatter;
	}
	public String getMobileStateFormatter() {
		if(null == mobileStateFormatter || "".equals(mobileStateFormatter)){
			return MOBILESTATE.getValue(getMobileState());
		}
		return this.mobileStateFormatter;
	}
	public void setMobileStateFormatter(String mobileStateFormatter) {
		this.mobileStateFormatter=mobileStateFormatter;
	}
	/**0,未认证:uncertified<br>1,待认证:waiting<br>2,已认证:certified<br>3,认证不通过:notPass**/
	public enum REALNAMESTATE {

		/**0,未认证:uncertified**/
		UNCERTIFIED("未认证",0),

		/**1,待认证:waiting**/
		WAITING("待认证",1),

		/**2,已认证:certified**/
		CERTIFIED("已认证",2),

		/**3,认证不通过:notPass**/
		NOTPASS("认证不通过",3);

		public final int code;
		public final String value;
		private static Map<Integer, String> map = new HashMap<Integer, String>();

		private REALNAMESTATE(String value, int code) {
			this.code = code;
			this.value = value;
		}

		public static String getValue(Integer code) {
			if (null == code) {
				return null;
			}
			for (REALNAMESTATE realnamestate : REALNAMESTATE.values()) {
				if (realnamestate.code == code) {
					return realnamestate.value;
				}
			}
			return null;
		}

		public static Integer getCode(String value) {
			if (null == value  || "".equals(value)) {
				return null;
			}
			for (REALNAMESTATE realnamestate : REALNAMESTATE.values()) {
				if (realnamestate.value.equals(value)) {
					return realnamestate.code;
				}
			}
			return null;
		}

		public static  Map<Integer, String> getEnumMap() {
			if(map.size() == 0){
				for (REALNAMESTATE realnamestate : REALNAMESTATE.values()) {
					map.put(realnamestate.code,realnamestate.value);
				}
			}
			return map;
		}
	}

	/**0,未认证:uncertified<br>1,待认证:waiting<br>2,已认证:certified<br>3,认证不通过:notPass**/
	public enum EMAILSTATE {

		/**0,未认证:uncertified**/
		UNCERTIFIED("未认证",0),

		/**1,待认证:waiting**/
		WAITING("待认证",1),

		/**2,已认证:certified**/
		CERTIFIED("已认证",2),

		/**3,认证不通过:notPass**/
		NOTPASS("认证不通过",3);

		public final int code;
		public final String value;
		private static Map<Integer, String> map = new HashMap<Integer, String>();

		private EMAILSTATE(String value, int code) {
			this.code = code;
			this.value = value;
		}

		public static String getValue(Integer code) {
			if (null == code) {
				return null;
			}
			for (EMAILSTATE emailstate : EMAILSTATE.values()) {
				if (emailstate.code == code) {
					return emailstate.value;
				}
			}
			return null;
		}

		public static Integer getCode(String value) {
			if (null == value  || "".equals(value)) {
				return null;
			}
			for (EMAILSTATE emailstate : EMAILSTATE.values()) {
				if (emailstate.value.equals(value)) {
					return emailstate.code;
				}
			}
			return null;
		}

		public static  Map<Integer, String> getEnumMap() {
			if(map.size() == 0){
				for (EMAILSTATE emailstate : EMAILSTATE.values()) {
					map.put(emailstate.code,emailstate.value);
				}
			}
			return map;
		}
	}

	/**0,未认证:uncertified<br>1,待认证:waiting<br>2,已认证:certified<br>3,认证不通过:notPass**/
	public enum MOBILESTATE {

		/**0,未认证:uncertified**/
		UNCERTIFIED("未认证",0),

		/**1,待认证:waiting**/
		WAITING("待认证",1),

		/**2,已认证:certified**/
		CERTIFIED("已认证",2),

		/**3,认证不通过:notPass**/
		NOTPASS("认证不通过",3);

		public final int code;
		public final String value;
		private static Map<Integer, String> map = new HashMap<Integer, String>();

		private MOBILESTATE(String value, int code) {
			this.code = code;
			this.value = value;
		}

		public static String getValue(Integer code) {
			if (null == code) {
				return null;
			}
			for (MOBILESTATE mobilestate : MOBILESTATE.values()) {
				if (mobilestate.code == code) {
					return mobilestate.value;
				}
			}
			return null;
		}

		public static Integer getCode(String value) {
			if (null == value  || "".equals(value)) {
				return null;
			}
			for (MOBILESTATE mobilestate : MOBILESTATE.values()) {
				if (mobilestate.value.equals(value)) {
					return mobilestate.code;
				}
			}
			return null;
		}

		public static  Map<Integer, String> getEnumMap() {
			if(map.size() == 0){
				for (MOBILESTATE mobilestate : MOBILESTATE.values()) {
					map.put(mobilestate.code,mobilestate.value);
				}
			}
			return map;
		}
	}


    public void setLoginStateFormatter(String realnameStateFormatter) {
        this.realnameStateFormatter = realnameStateFormatter;
    }

    public UserIdentify() {
    }

    public enum STATE {

        UNIDENTIFY("未认证", 0),
        WAIT_IDENTIFY("待认证", 1),
        IDENTIFIED("已认证", 2),
        NO_PASS("认证不通过", 3);

        public final int code;
        public final String value;
        private static Map<Integer, String> map = new HashMap<Integer, String>();

        private STATE(String value, int code) {
            this.code = code;
            this.value = value;
        }

        public static String getValue(Integer code) {
            if (null == code) {
                return null;
            }
            for (UserIdentify.STATE state : UserIdentify.STATE.values()) {
                if (state.code == code) {
                    return state.value;
                }
            }
            return null;
        }

        public static Integer getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (UserIdentify.STATE state : UserIdentify.STATE.values()) {
                if (state.value.equals(value)) {
                    return state.code;
                }
            }
            return null;
        }

        public static Map<Integer, String> getEnumMap() {
            if (map.size() == 0) {
                for (UserIdentify.STATE state : UserIdentify.STATE.values()) {
                    map.put(state.code, state.value);
                }
            }
            return map;
        }
    }

    public UserIdentify(Consumer<UserIdentify> consumer){
    consumer.accept(this);
    }
}

