using System.Collections;
using UnityEngine.UI;
using UnityEngine;
using Login;
using UnityEngine.Networking;
using static Login.LoginManager;

public class SaveTestResult : MonoBehaviour
{

    public LoginManager loginManager;
    public TestResult testResult;

    public TestResult testTestResult = new TestResult(new TestResult.Twohand(true, 0, 200), new TestResult.Conveyor(true, 1234), new TestResult.DigitSpan(true), new TestResult.Maze(false, 6), new TestResult.DecisionMaking(true, 123, 2, 3));



    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void Save()
    {
        print("clicked");
         TestResult testResult = new TestResult(
            new TestResult.Twohand(Menu_UI.conveyor_web_result, Conveyor_button.err_count, Conveyor_button.save_time), 
            new TestResult.Conveyor(Menu_UI.two_hand_web_result, UI_control.display_time,
            new TestResult.DigitSpan(true), 
            new TestResult.Maze(Menu_UI.maze_web_result, Maze_move.Trigger_Count), 
            new TestResult.DecisionMaking(true, 123, 2, 3));

        StartCoroutine(SendTestResultTest(testResult));
    }
    IEnumerator SendTestResultTest(TestResult testResult)
    {
        print("coroutine start");
        // 백엔드 서버 주소
        string host = "oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080";
        string uri = "/subject/test-result";
        string url = "https://" + host + uri;
        // post form 내용 구성
        WWWForm form = new WWWForm();
        string result = JsonUtility.ToJson(testResult);
        print(result);

        print(LoginManager.loginToken);

        if (LoginManager.loginToken!=null)
        {
            // form을 이용해서 해당 주소로 요청 보냄
            UnityWebRequest www = UnityWebRequest.Post(url, result);
            byte[] JsonToSend = new System.Text.UTF8Encoding().GetBytes(result);
            www.uploadHandler = new UploadHandlerRaw(JsonToSend);
            www.SetRequestHeader("Content-Type", "application/json");
            www.SetRequestHeader("Authorization", loginToken);

            yield return www.SendWebRequest();

            // 에러가 없으면 결과를 받음.
            if (www.error == null)
            {
                print("json:" + www.downloadHandler.text);
                string data = www.downloadHandler.text;

                // json형태의 결과를 받아 역직렬화함.
                JsonResult jsonResult = JsonUtility.FromJson<JsonResult>(data);
                print(jsonResult.msg);
                print(jsonResult.results.username);
                print(jsonResult.results.token);
                print(jsonResult.statusCode);
            }
            else
            {
                print(www.error);
            }
        }


    }
}
