# CatPals 重命名需修改文件清单

AB3 → CatPals 时：**你改 classname**，**我（你）负责改 update fields 功能**。下面列出所有需要动到的文件，按修改类型分组。字段（Person 的 name/phone/email 等）由你后续改，这里只列 **包名、类名、项目名、存储路径** 相关。

---

## 一、包名整体迁移：`seedu.address` → `seedu.catpals`

所有带 `package seedu.address` 或 `import seedu.address` 的文件都要改。  
**做法**：把目录 `src/main/java/seedu/address` 重命名为 `src/main/java/seedu/catpals`，`src/test/java/seedu/address` → `src/test/java/seedu/catpals`，然后全局替换：
- `seedu.address` → `seedu.catpals`

### 1. 主代码 (src/main/java/seedu/address/) — 共约 70 个 .java

| 文件 | 说明 |
|------|------|
| Main.java | 入口，包名 + 若有 address 文案可改 |
| MainApp.java | 包名 + import + 日志里 "AddressBook" 等 |
| AppParameters.java | 包名 |
| **model/** | |
| model/AddressBook.java | 类名 → CatPals（见第二节） |
| model/ReadOnlyAddressBook.java | → ReadOnlyCatPals |
| model/Model.java | 包名 + AddressBook 相关接口 |
| model/ModelManager.java | 包名 + addressBook 字段/方法 |
| model/UserPrefs.java | 包名 + addressBookFilePath / addressbook.json |
| model/ReadOnlyUserPrefs.java | 包名 |
| model/util/SampleDataUtil.java | 包名 + AddressBook / getSampleAddressBook |
| model/person/*.java | 包名（Person 等字段类你后面改） |
| model/tag/Tag.java | 包名 |
| **logic/** | |
| logic/Logic.java | 包名 + getAddressBook 等 |
| logic/LogicManager.java | 包名 + AddressBook 相关 |
| logic/Messages.java | 包名 |
| logic/commands/*.java | 包名 + 若有 AddressBook/address 文案 |
| logic/parser/*.java | 包名 + AddressBookParser 类名（见下） |
| **storage/** | |
| storage/Storage.java | 包名 + AddressBook 接口方法 |
| storage/AddressBookStorage.java | 类名 → CatPalsStorage |
| storage/StorageManager.java | 包名 + AddressBook 相关 |
| storage/JsonAddressBookStorage.java | 类名 → JsonCatPalsStorage |
| storage/JsonSerializableAddressBook.java | 类名 → JsonSerializableCatPals + @JsonRootName |
| storage/JsonAdaptedPerson.java | 包名（字段你改） |
| storage/JsonAdaptedTag.java | 包名 |
| storage/JsonUserPrefsStorage.java | 包名 |
| storage/UserPrefsStorage.java | 包名 |
| **ui/** | |
| ui/*.java | 包名（PersonCard 等可等你改 Person→Cat 再一起改） |
| **commons/** | |
| commons/core/Config.java, LogsCenter.java, Version.java, GuiSettings.java, index/Index.java | 包名；LogsCenter 里 `addressbook.log` → `catpals.log` |
| commons/util/*.java | 包名 |
| commons/exceptions/*.java | 包名 |

### 2. 测试代码 (src/test/java/seedu/address/) — 所有 .java

- 同上：包名 `seedu.address` → `seedu.catpals`
- 所有引用 `AddressBook` / `ReadOnlyAddressBook` / `getTypicalAddressBook` / `getAddressBook()` 等的测试类都要改成新类名/新方法名（见第二节）。

涉及文件包括但不限于：
- AddressBookTest.java, ModelManagerTest.java, LogicManagerTest.java
- AddCommandTest.java, AddCommandIntegrationTest.java, ClearCommandTest.java, DeleteCommandTest.java, EditCommandTest.java, FindCommandTest.java, ListCommandTest.java, HelpCommandTest.java, ExitCommandTest.java
- AddressBookParserTest.java, JsonAddressBookStorageTest.java, JsonSerializableAddressBookTest.java, JsonAdaptedPersonTest.java
- CommandTestUtil.java, EditPersonDescriptorTest.java
- testutil: AddressBookBuilder.java, TypicalPersons.java, PersonBuilder.java, EditPersonDescriptorBuilder.java, PersonUtil.java 等
- 以及所有其它在 `seedu.address` 下的测试类

---

## 二、类名 / 接口名替换（与包名一起做）

| 原名称 | 新名称 |
|--------|--------|
| AddressBook | CatPals |
| ReadOnlyAddressBook | ReadOnlyCatPals |
| AddressBookStorage | CatPalsStorage |
| JsonAddressBookStorage | JsonCatPalsStorage |
| JsonSerializableAddressBook | JsonSerializableCatPals |
| AddressBookParser | CatPalsParser（或 CommandParser） |

方法名 / 变量名建议一并改，避免混用：
- `getAddressBook()` → `getCatPals()`
- `setAddressBook(...)` → `setCatPals(...)`
- `readAddressBook()` / `saveAddressBook()` → `readCatPals()` / `saveCatPals()`
- `getAddressBookFilePath()` → `getCatPalsFilePath()`（或 `getDataFilePath()`）
- `addressBookFilePath` → `catPalsFilePath`（或 `dataFilePath`）

**Person** 是否改为 **Cat**（以及 PersonCard、JsonAdaptedPerson 等）由你「改 update fields」时一起决定，这里不强制。

---

## 三、配置与构建

| 文件 | 修改内容 |
|------|----------|
| **build.gradle** | `mainClassName = 'seedu.address.Main'` → `'seedu.catpals.Main'`；`archiveFileName = 'addressbook.jar'` → `'catpals.jar'` |
| **src/main/java/.../UserPrefs.java** | `addressBookFilePath = Paths.get("data", "addressbook.json")` → `Paths.get("data", "catpals.json")`；getter/setter 名见上 |
| **src/main/java/.../commons/core/LogsCenter.java** | `LOG_FILE = "addressbook.log"` → `"catpals.log"` |
| **src/main/java/.../storage/JsonSerializableAddressBook.java** | `@JsonRootName(value = "addressbook")` → `"catpals"`（若 JSON 根键用 catpals）；内部类名已在上表改为 JsonSerializableCatPals |

---

## 四、资源与数据文件

| 文件 | 说明 |
|------|------|
| **src/main/resources/** | 若 FXML/资源里写死 `seedu.address` 包名或 AddressBook 字样，改为 seedu.catpals / CatPals |
| **src/test/data/JsonUserPrefsStorageTest/TypicalUserPref.json** | `"addressBookFilePath"` → `"catPalsFilePath"`（或你定的 key）；`"addressbook.json"` → `"catpals.json"` |
| **src/test/data/JsonUserPrefsStorageTest/ExtraValuesUserPref.json** | 同上，若有 addressBook 相关键 |
| **src/test/data/JsonSerializableAddressBookTest/*.json** | 若 JSON 根从 "addressbook" 改为 "catpals"，这里要一致；内部 "persons" 可等你改 model 再改成 "cats" |
| **src/test/data/JsonAddressBookStorageTest/*.json** | 同上，路径/键名与 storage 实现一致 |

本地运行时数据：
- **data/addressbook.json** → 重命名或改为 **data/catpals.json**，并在 UserPrefs 里默认路径一致（你已删过旧文件，新默认用 catpals.json 即可）。

---

## 五、文档（可选但建议统一）

| 文件 | 修改内容 |
|------|----------|
| README.md | 项目名、描述、AddressBook → CatPals |
| docs/UserGuide.md | 产品名、指令说明中的 “address book” 等 |
| docs/DeveloperGuide.md | 架构说明、类图里的 AddressBook、AddressBook 相关描述 |
| docs/SettingUp.md | 若有 “AddressBook” 项目名 |
| docs/Testing.md | 若有 seedu.address 或 AddressBook |
| docs/DevOps.md, docs/index.md | 项目名、链接 |
| docs/_config.yml, docs/_data/projects.yml | 站点/项目标题 |
| docs/team/johndoe.md | 项目名、描述 |
| docs/diagrams/*.puml | 类名/参与者名：AddressBook → CatPals，AddressBookParser → CatPalsParser，ReadOnlyAddressBook → ReadOnlyCatPals 等 |

---

## 六、建议执行顺序

1. **包名 + 目录**：`seedu/address` → `seedu/catpals`（含 main 和 test）。
2. **类名/接口名**：第二节表格全局替换（含方法名 getAddressBook → getCatPals 等）。
3. **配置与存储**：build.gradle、UserPrefs、LogsCenter、JsonSerializableAddressBook 的 @JsonRootName 和默认路径、jar 名。
4. **测试数据 JSON**：UserPrefs 的 key/file 名、若改 JSON 根则一并改测试 json。
5. **文档与 .puml**：按需统一为 CatPals。

这样改完后，**classname 和项目名** 都变成 CatPals；**update fields 功能**（Person 的字段、命令参数等）由你单独改，和这份清单不冲突。
