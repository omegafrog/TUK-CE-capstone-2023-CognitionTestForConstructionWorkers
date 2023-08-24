using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
namespace Login
{
    public class LoginManager : MonoBehaviour
    {
        public static string loginToken;
        public InputField username;
        public InputField password;
        public static string ID_save;

        // Start is called before the first frame update
        void Start()
        {
            
        }

        // Update is called once per frame
        void Update()
        {

        }

        [System.Serializable]
        public class JsonResult
        {
            [System.Serializable]
            public class LoginResult
            {
                public string token;
                public string username;
            }
            public string msg;
            public LoginResult results;
            public int statusCode;
        }


        IEnumerator SubjectLoginTestCode()
        {
            print("coroutine start");
            // 백엔드 서버 주소
            string host = "oiwaejofenwiaovjsoifaoiwnfiofweafj.site:8080";
            string uri = "/subject/login";
            string url = "https://" + host + uri;
            // post form 내용 구성
            WWWForm form = new WWWForm();
            // username, password로 로그인 할 정보 사용자에게 받아야 함.

            if (username != null && password != null)
            {
                // 이걸로 form에다가 로그인 정보 넣음.
                form.AddField("username", username.text);
                form.AddField("password", password.text);
                ID_save = username.text;
                // form을 이용해서 해당 주소로 요청 보냄
                UnityWebRequest www = UnityWebRequest.Post(url, form);

                yield return www.SendWebRequest();

                // 에러가 없으면 결과를 받음.
                if (www.error == null)
                {
                    print("json:" + www.downloadHandler.text);
                    string data = www.downloadHandler.text;
                    // json형태의 결과를 받아 역직렬화함.
                    JsonResult result = JsonUtility.FromJson<JsonResult>(data);
                    print(result.msg);
                    print(result.results.username);
                    print(result.results.token);
                    print(result.statusCode);
                    // 로그인 시에 result.results.token을 함께 건네줘야 함.
                    // 그래서 전역변수로 토큰을 저장함.
                    loginToken = result.results.token;
                    SceneManager.LoadScene("Main_menu", LoadSceneMode.Single);
                    print("로그인 종료");
                }
                else
                {
                    print(www.error);
                }
            }
        }

        // Update is called once per frame

        public void SendRequest()
        {
            print("click");
            StartCoroutine(SubjectLoginTestCode());
        }
    }

}


//SceneManager.LoadScene("Main_menu", LoadSceneMode.Single); //로그인 확인 코드뒤 삽입